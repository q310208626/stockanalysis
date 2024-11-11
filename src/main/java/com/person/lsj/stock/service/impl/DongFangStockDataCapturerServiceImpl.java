package com.person.lsj.stock.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.person.lsj.stock.bean.dongfang.HomePageBean;
import com.person.lsj.stock.bean.dongfang.StockField;
import com.person.lsj.stock.bean.dongfang.data.*;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowDeserializer;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.task.*;
import com.person.lsj.stock.constant.Constant;
import com.person.lsj.stock.constant.StockStatus;
import com.person.lsj.stock.service.RedisOpsService;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Primary
public class DongFangStockDataCapturerServiceImpl implements StockDataCapturerService {
    private static Logger LOGGER = Logger.getLogger(StockDataCapturerService.class);

    private static final String STOCK_HOST = "https://43.push2.eastmoney.com";

    // get StockCodeList
    private static final String HOME_PAGE_URL = STOCK_HOST + "/api/qt/clist/get";

    private static final String STOCK_FLOW_HOST = "https://push2his.eastmoney.com";

    // get Stock Money Flow
    private static final String STOCK_MONEY_FLOW_URL = STOCK_FLOW_HOST + "/api/qt/stock/fflow/daykline/get";

    private static final String STOCK_DETAILS_HOST = "https://push2his.eastmoney.com";

    // get Stock Details
    private static final String STOCK_DETAILS_URL = STOCK_DETAILS_HOST + "/api/qt/stock/kline/get";

    // get cur Stock Details
    private static final String STOCK_CUR_DETAILS_URL = STOCK_HOST + "/api/qt/stock/get";

    private static final Header userAgentHeader = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.6422.112 Safari/537.36");

    // target funds imcrement 1000w
    private static int TARGET_3DAY_MONEY_FLOW_INCREMENT = 10000000;

    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Constant.CPU_CORE_COUNT, Constant.CPU_CORE_COUNT * 2, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5000));

    @Autowired
    private RedisOpsService redisOpsService;

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public int getTotalPage() {
        LOGGER.debug("Enter getTotalPage");
        int totalPage = 0;
        try {
            String curUrl = String.format(HOME_PAGE_URL);

            URIBuilder builder = new URIBuilder(curUrl);

            // curPageNum
            builder.addParameter("pn", "1");

            // pageSize
            builder.addParameter("pz", String.valueOf(Constant.PAGE_SIZE));

            // sotck board 1=shanghai
            builder.addParameter("po", "1");
            builder.addParameter("np", "1");
            builder.addParameter("fltt", "2");
            builder.addParameter("invt", "2");
            builder.addParameter("dect", "1");
            builder.addParameter("wbp2u", "|0|0|0|web");
            builder.addParameter("fid", "f3");
            builder.addParameter("fs", "m:1+t:2,m:1+t:23,m:0+t:6");
            builder.addParameter("fields", "f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152");
            HttpGet httpGet = new HttpGet(builder.build());

            // header
            httpGet.addHeader(userAgentHeader);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                 InputStream content = httpResponse.getEntity().getContent();
                 InputStreamReader reader = new InputStreamReader(content);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                String line = null;
                StringBuffer dataBuffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    dataBuffer.append(line);
                    dataBuffer.append(System.lineSeparator());
                }

                ObjectMapper objectMapper = new ObjectMapper();
                HomePageBean homePageBean = objectMapper.readValue(dataBuffer.toString(), HomePageBean.class);
                int total = homePageBean.getData() == null ? 0 : homePageBean.getData().getTotal();
                totalPage = total % Constant.PAGE_SIZE == 0 ? total / Constant.PAGE_SIZE : total / Constant.PAGE_SIZE + 1;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.debug("Exit getTotalPage");
        return totalPage;
    }

    @Override
    public List<String> getAllStockCodes() {
        LOGGER.debug("Enter getAllStockCodes");
        int totalPage = getTotalPage();
        List<String> stockCodes = new ArrayList<>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(totalPage);
            List<Future<List<String>>> futureList = new ArrayList<>();
            for (int page = 1; page <= totalPage; page++) {
                Future<List<String>> futureTask = threadPool.submit(new GetStockCodeList(page, countDownLatch));
                futureList.add(futureTask);
            }

            countDownLatch.await();

            for (Future<List<String>> listFuture : futureList) {
                List<String> pageStockCodes = listFuture.get();
                stockCodes.addAll(pageStockCodes);
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        LOGGER.debug("Exit getAllStockCodes");
        return stockCodes;
    }

    @Override
    public List<String> getStockCode(int page) {
        LOGGER.debug("Enter getStockCode");
        List<String> stockCodes = new ArrayList<>();
        try {
            String curUrl = String.format(HOME_PAGE_URL);

            URIBuilder builder = new URIBuilder(curUrl);

            // curPageNum
            builder.addParameter("pn", String.valueOf(page));

            // pageSize
            builder.addParameter("pz", String.valueOf(Constant.PAGE_SIZE));

            // sotck board 1=shanghai
            builder.addParameter("po", "1");
            builder.addParameter("np", "1");
            builder.addParameter("fltt", "2");
            builder.addParameter("invt", "2");
            builder.addParameter("dect", "1");
            builder.addParameter("wbp2u", "|0|0|0|web");
            builder.addParameter("fid", "f3");
            builder.addParameter("fs", "m:1+t:2,m:1+t:23,m:0+t:6");

            // f292 状态 dealTradeStae 6停牌 7退市
            builder.addParameter("fields", "f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152,f292");
            HttpGet httpGet = new HttpGet(builder.build());

            // header
            httpGet.addHeader(userAgentHeader);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                 InputStream content = httpResponse.getEntity().getContent();
                 InputStreamReader reader = new InputStreamReader(content);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                String line = null;
                StringBuffer dataBuffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    dataBuffer.append(line);
                    dataBuffer.append(System.lineSeparator());
                }

                ObjectMapper objectMapper = new ObjectMapper();
                HomePageBean homePageBean = objectMapper.readValue(dataBuffer.toString(), HomePageBean.class);
                StockField[] stockFields = homePageBean.getData() == null ? null : homePageBean.getData().getDiff();

                if (null != stockFields) {
                    List<String> stockCodeList = Arrays.stream(stockFields)
                            .filter(x -> x.getStatus() != StockStatus.SUSPEND.status)
                            .filter(x -> x.getStatus() != StockStatus.DELISTED.status)
                            .map(x -> x.getStockCode()).collect(Collectors.toList());
                    stockCodes.addAll(stockCodeList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.debug("Exit getStockCode");
        return stockCodes;
    }

    @Override
    public List<String> getStockMoneyFlowUp(List<String> stockCodes) {
        LOGGER.debug("Enter getStockMoneyFlowUp");

        List<String> moneyUpStockCodes = new ArrayList<>();
        ;
        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_MONEY_FLOW_URL);

            // 获取10天的数据
            uriBuilder.addParameter("lmt", "10");

            uriBuilder.addParameter("fields1", "f1,f2,f3,f7");
            uriBuilder.addParameter("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61,f62,f63,f64,f65");
            for (String stockCode : stockCodes) {
                LOGGER.debug("start get money flow msg:" + stockCode);
                uriBuilder.setParameter("secid", getRequestSecid(stockCode));
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                threadPool.execute(new JudgeCurStockMoneyFlowTask(moneyUpStockCodes, stockCode, httpGet));
                LOGGER.debug("end money flow msg:" + stockCode);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {

        }

        LOGGER.debug("Exit getStockMoneyFlowUp");
        return moneyUpStockCodes;
    }

    @Override
    public List<String> getStockV6Detail(List<String> stockCodes) {
        LOGGER.debug("Enter getStockV6Detail");
        List<String> detailsGoodStockCodes = new ArrayList<>();

        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_DETAILS_URL);
            uriBuilder.addParameter("fields1", "f1,f2,f3,f4,f5,f6");
            uriBuilder.addParameter("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61");
            uriBuilder.addParameter("klt", "101");
            uriBuilder.addParameter("fqt", "1");
            uriBuilder.addParameter("end", "20500101");

            // 获取30天的数据
            uriBuilder.addParameter("lmt", "120");

            for (String stockCode : stockCodes) {
                LOGGER.debug("Start getStockV6Detail:" + stockCode);
                uriBuilder.setParameter("secid", getRequestSecid(stockCode));
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                threadPool.submit(new JudgeCurStockDetailsTask(detailsGoodStockCodes, stockCode, httpGet));
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.debug("Exit getStockV6Detail");
        return detailsGoodStockCodes;
    }

    @Override
    public Map<String, StockDetailsData> getStockCodesV6Detail(List<String> stockCodes) {
        if (CollectionUtils.isEmpty(stockCodes)) {
            return Map.of();
        }

        Map<String, StockDetailsData> stockDetailsDataMap = new HashMap<>();
        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_DETAILS_URL);
            uriBuilder.addParameter("fields1", "f1,f2,f3,f4,f5,f6");
            uriBuilder.addParameter("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61");
            uriBuilder.addParameter("klt", "101");
            uriBuilder.addParameter("fqt", "1");
            uriBuilder.addParameter("end", "20500101");

            // 获取30天的数据
            uriBuilder.addParameter("lmt", "120");
            List<FutureTask<StockDetailsData>> futureTaskList = new ArrayList<>();
            CountDownLatch countDownLatch = new CountDownLatch(stockCodes.size());
            for (String stockCode : stockCodes) {
                uriBuilder.setParameter("secid", getRequestSecid(stockCode));
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                FutureTask<StockDetailsData> futureTask = new FutureTask<>(new GetStockCodeV6Details(stockCode, httpGet, countDownLatch));
                futureTaskList.add(futureTask);
                threadPool.submit(futureTask);
            }

            countDownLatch.await();

            for (FutureTask<StockDetailsData> futureTask : futureTaskList) {
                StockDetailsData stockDetailsData = futureTask.get(20, TimeUnit.MILLISECONDS);
                stockDetailsDataMap.put(stockDetailsData.getStockCode(), stockDetailsData);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        return stockDetailsDataMap;
    }

    // 获取请求参数Secid
    private static String getRequestSecid(String stockCode) {
        if (stockCode.startsWith("0")) {
            return "0." + stockCode;
        } else if (stockCode.startsWith("BK")) {
            return "90." + stockCode;
        } else {
            return "1." + stockCode;
        }
    }

    @Override
    public Map<String, String> getStockCodesNames(List<String> stockCodes) {
        if (CollectionUtils.isEmpty(stockCodes)) {
            return Map.of();
        }

        Map<String, String> stocksName = new HashMap<>();
        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_DETAILS_URL);
            uriBuilder.addParameter("fields1", "f1,f2,f3,f4,f5,f6");
            uriBuilder.addParameter("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61");
            uriBuilder.addParameter("klt", "101");
            uriBuilder.addParameter("fqt", "1");
            uriBuilder.addParameter("end", "20500101");

            // 获取30天的数据
            uriBuilder.addParameter("lmt", "120");
            List<FutureTask<StockDetailsData>> futureTaskList = new ArrayList<>();
            CountDownLatch countDownLatch = new CountDownLatch(stockCodes.size());
            for (String stockCode : stockCodes) {
                uriBuilder.setParameter("secid", getRequestSecid(stockCode));
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                FutureTask<StockDetailsData> futureTask = new FutureTask<>(new GetStockCodeName(stockCode, httpGet, countDownLatch, redisOpsService));
                futureTaskList.add(futureTask);
                threadPool.submit(futureTask);
            }

            countDownLatch.await();

            for (FutureTask<StockDetailsData> futureTask : futureTaskList) {
                StockDetailsData stockDetailsData = futureTask.get(20, TimeUnit.MILLISECONDS);
                stocksName.put(stockDetailsData.getStockCode(), stockDetailsData.getStockName());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        return stocksName;
    }

    @Override
    public Map<String, StockCurDetailsData> getStockCodesCurDayDetail(List<String> stockCodes) {
        LOGGER.debug("Enter getStockCodesCurDayDetail");
        if (CollectionUtils.isEmpty(stockCodes)) {
            return Map.of();
        }

        Map<String, StockCurDetailsData> stockDetailsDataMap = new HashMap<>();
        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_CUR_DETAILS_URL);
            uriBuilder.addParameter("fields", "f43,f44,f45,f46,f47,f57,f58,f170");
            uriBuilder.addParameter("invt", "2");
            uriBuilder.addParameter("fltt", "1");
            uriBuilder.addParameter("wbp2u", "|0|0|0|web");
            uriBuilder.addParameter("dect", "1");

            List<FutureTask<StockCurDetailsData>> futureTaskList = new ArrayList<>();
            CountDownLatch countDownLatch = new CountDownLatch(stockCodes.size());
            for (String stockCode : stockCodes) {
                uriBuilder.setParameter("secid", getRequestSecid(stockCode));
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                FutureTask<StockCurDetailsData> futureTask = new FutureTask<>(new GetStockCodeCurDayDetails(stockCode, httpGet, countDownLatch));
                futureTaskList.add(futureTask);
                threadPool.submit(futureTask);
            }

            countDownLatch.await();

            for (FutureTask<StockCurDetailsData> futureTask : futureTaskList) {
                StockCurDetailsData stockDetailsData = futureTask.get(20, TimeUnit.MILLISECONDS);
                stockDetailsDataMap.put(stockDetailsData.getStockCode(), stockDetailsData);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        LOGGER.debug("Exit getStockCodesCurDayDetail");
        return stockDetailsDataMap;
    }

    // 判断资金是否有添加
    class JudgeCurStockMoneyFlowTask implements Runnable {
        private List<String> detailsGoodStockCodes;
        private String curStockCode;
        private HttpGet httpGet;

        public JudgeCurStockMoneyFlowTask(List<String> detailsGoodStockCodes, String curStockCode, HttpGet httpGet) {
            this.detailsGoodStockCodes = detailsGoodStockCodes;
            this.curStockCode = curStockCode;
            this.httpGet = httpGet;
        }

        @Override
        public void run() {
            LOGGER.debug("start judge money flow:" + curStockCode);
            judgeStockMoneyFlow(detailsGoodStockCodes, curStockCode, httpGet);
            LOGGER.debug("end judge money flow:" + curStockCode);
        }
    }

    private void judgeStockMoneyFlow(List<String> moneyUpStockCodes, String stockCode, HttpGet httpGet) {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
             CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
             InputStreamReader inputStreamReader = new InputStreamReader(closeableHttpResponse.getEntity().getContent());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(StockMoneyFlowBean.class, new StockMoneyFlowDeserializer());
            mapper.registerModule(module);
            StockMoneyFlowBean stockMoneyFlowBean = mapper.readValue(buffer.toString(), StockMoneyFlowBean.class);

            List<StockMoneyFlowData> stockMoneyFlowBeanDatas = stockMoneyFlowBean.getDatas();
            if (!CollectionUtils.isEmpty(stockMoneyFlowBeanDatas)) {
                int main3DayMoneyFlow = 0;
                for (int i = 0; i < (stockMoneyFlowBeanDatas.size() < 3 ? stockMoneyFlowBeanDatas.size() : 3); i++) {
                    StockMoneyFlowData stockMoneyFlowData = stockMoneyFlowBeanDatas.get(i);
                    main3DayMoneyFlow += stockMoneyFlowData.getMainMoneyFlow();
                }

                if (main3DayMoneyFlow >= TARGET_3DAY_MONEY_FLOW_INCREMENT) {
                    moneyUpStockCodes.add(stockCode);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStockResultDetails(List<StockDataResultDetails> stockDataResultDetailsList) {
        LOGGER.debug("Enter setStockResultDetails");

        for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {

        }

        LOGGER.debug("Exit setStockResultDetails");
    }

    @Override
    public List<StockMoneyFlowBean> getStockMoneyFlowData(List<String> stockCodes) {
        LOGGER.debug("Enter getStockMoneyFlowData");

        List<StockMoneyFlowBean> stockMoneyFlowBeanList = new ArrayList<>();
        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_MONEY_FLOW_URL);

            // 获取10天的数据
            uriBuilder.addParameter("lmt", "10");

            uriBuilder.addParameter("fields1", "f1,f2,f3,f7");
            uriBuilder.addParameter("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61,f62,f63,f64,f65");
            CountDownLatch countDownLatch = new CountDownLatch(stockCodes.size());
            List<FutureTask<StockMoneyFlowBean>> futureTaskArrayList = new ArrayList<>();
            for (String stockCode : stockCodes) {
                LOGGER.debug("start get money flow msg:" + stockCode);
                uriBuilder.setParameter("secid", getRequestSecid(stockCode));
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                FutureTask<StockMoneyFlowBean> stockMoneyFlowBeanFutureTask = new FutureTask<>(new GetStockCodeMoneyData(stockCode, httpGet, countDownLatch));
                futureTaskArrayList.add(stockMoneyFlowBeanFutureTask);
                threadPool.execute(stockMoneyFlowBeanFutureTask);
                LOGGER.debug("end money flow msg:" + stockCode);
            }

            countDownLatch.await();

            for (FutureTask<StockMoneyFlowBean> stockMoneyFlowBeanFuture : futureTaskArrayList) {
                StockMoneyFlowBean stockMoneyFlowBean = stockMoneyFlowBeanFuture.get(20, TimeUnit.MILLISECONDS);
                stockMoneyFlowBeanList.add(stockMoneyFlowBean);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        LOGGER.debug("Exit getStockMoneyFlowData");
        return stockMoneyFlowBeanList;
    }

    @Override
    public LocalDate getLastWorkDay() {
        List<String> stockCodeList = getStockCode(1);
        Map<String, StockDetailsData> stockCodesV6DetailMap = getStockCodesV6Detail(stockCodeList);
        Collection<StockDetailsData> stockCodesV6Detail = stockCodesV6DetailMap.values();
        LocalDate lastWorkDay = null;
        LocalDate today = LocalDate.now();
        boolean hasGetLastWorkDay = false;
        for (StockDetailsData stockDetailsData : stockCodesV6Detail) {
            List<StockDataEntity> stockDataEntities = stockDetailsData.getStockDataEntities();
            for (int i = stockDataEntities.size() - 1; i >= 0; i--) {
                lastWorkDay = stockDataEntities.get(i).getTime();

                if (lastWorkDay != null && !today.isEqual(lastWorkDay)) {
                    hasGetLastWorkDay = true;
                    break;
                }
            }

            if (hasGetLastWorkDay) {
                break;
            }
        }
        return lastWorkDay;
    }

    @Override
    public Integer getStockStatus(String stockCode) {
        LOGGER.debug("Enter getStockStatus");
        stockCode = Optional.ofNullable(stockCode).orElse("600028");
        int stockStatus = StockStatus.CLOSED.status;

        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_CUR_DETAILS_URL);
            uriBuilder.addParameter("fields", "f292");
            uriBuilder.addParameter("invt", "2");
            uriBuilder.addParameter("fltt", "1");
            uriBuilder.addParameter("wbp2u", "|0|0|0|web");
            uriBuilder.addParameter("dect", "1");

            uriBuilder.setParameter("secid", getRequestSecid(stockCode));
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(httpGet);
                 InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

                StringBuffer contentBuffer = new StringBuffer();
                String content = null;
                while ((content = bufferedReader.readLine()) != null) {
                    contentBuffer.append(content);
                }

                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addDeserializer(StockCurDetailsData.class, new StockCurDetailsDeserializer());
                mapper.registerModule(module);
                StockCurDetailsData stockCurDetailsData = mapper.readValue(contentBuffer.toString(), StockCurDetailsData.class);
                stockStatus = stockCurDetailsData.getStockStatus();

            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.debug("Exit getStockStatus");
        return stockStatus;
    }

    @Override
    public int getBoardsTotalPage(int boardType) {
        LOGGER.debug("Start get getBoardsTotalPage:" + boardType);
        int totalPage = 0;
        String curUrl = String.format(HOME_PAGE_URL);
        try {
            URIBuilder builder = new URIBuilder(curUrl);

            // pageSize
            builder.addParameter("pz", String.valueOf(Constant.PAGE_SIZE));

            // sotck board 1=shanghai
            builder.addParameter("po", "1");
            builder.addParameter("np", "1");
            builder.addParameter("fltt", "2");
            builder.addParameter("invt", "2");
            builder.addParameter("dect", "1");
            builder.addParameter("wbp2u", "|0|0|0|web");
            builder.addParameter("fid", "f3");
            if (boardType == Constant.BOARD_TYPE_INDUSTRY) {
                builder.addParameter("fs", "m:90+t:2+f:!50");
            } else {
                builder.addParameter("fs", "m:90+t:3+f:!50");
            }

            // curPageNum
            builder.addParameter("pn", String.valueOf(1));

            // f292 状态 dealTradeStae 6停牌 7退市
            builder.addParameter("fields", "f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152,f124,f107,f104,f105,f140,f141,f207,f208,f209,f222");
            HttpGet httpGet = new HttpGet(builder.build());
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                 InputStream content = httpResponse.getEntity().getContent();
                 InputStreamReader reader = new InputStreamReader(content);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                String line = null;
                StringBuffer dataBuffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    dataBuffer.append(line);
                    dataBuffer.append(System.lineSeparator());
                }

                JSONObject jsonObject = JSONObject.parseObject(dataBuffer.toString());
                JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                String pageStr = dataJsonObject.getString("total");
                int total = Integer.parseInt(pageStr);
                totalPage = total % Constant.PAGE_SIZE == 0 ? total / Constant.PAGE_SIZE : total / Constant.PAGE_SIZE + 1;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        LOGGER.debug("End get getBoardsTotalPage");
        return totalPage;
    }

    @Override
    public List<StockBoardBean> getAllStockBoards(int boardType) {
        LOGGER.debug("Enter getAllStockBoards");
        int totalPage = getBoardsTotalPage(boardType);
        List<StockBoardBean> stockBoardBeans = new ArrayList<>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(totalPage);
            List<Future<List<StockBoardBean>>> futureList = new ArrayList<>();
            for (int page = 1; page <= totalPage; page++) {
                Future<List<StockBoardBean>> futureTask = threadPool.submit(new GetStockBoards(boardType, page, countDownLatch));
                futureList.add(futureTask);
            }

            countDownLatch.await();

            for (Future<List<StockBoardBean>> listFuture : futureList) {
                List<StockBoardBean> stockBoardBeanList = listFuture.get();
                stockBoardBeans.addAll(stockBoardBeanList);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        LOGGER.debug("Exit getAllStockBoards");
        return stockBoardBeans;
    }

    @Override
    public Map<String, StockDetailsData> getStockBoardsV6Details(int boardType) {
        List<StockBoardBean> allStockBoards = getAllStockBoards(boardType);

        if (CollectionUtils.isEmpty(allStockBoards)) {
            return Map.of();
        }

        Map<String, StockDetailsData> stockDetailsDataMap = new HashMap<>();
        try {
            URIBuilder uriBuilder = new URIBuilder(STOCK_DETAILS_URL);
            uriBuilder.addParameter("fields1", "f1,f2,f3,f4,f5,f6");
            uriBuilder.addParameter("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61");
            uriBuilder.addParameter("klt", "101");
            uriBuilder.addParameter("fqt", "1");
            uriBuilder.addParameter("end", "20500101");

            // 获取30天的数据
            uriBuilder.addParameter("lmt", "120");
            List<FutureTask<StockDetailsData>> futureTaskList = new ArrayList<>();
            CountDownLatch countDownLatch = new CountDownLatch(allStockBoards.size());
            for (StockBoardBean stockBoardBean : allStockBoards) {
                String boardCode = stockBoardBean.getBoardCode();
                uriBuilder.setParameter("secid", getRequestSecid(boardCode));
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                FutureTask<StockDetailsData> futureTask = new FutureTask<>(new GetStockCodeV6Details(boardCode, httpGet, countDownLatch));
                futureTaskList.add(futureTask);
                threadPool.submit(futureTask);
            }

            countDownLatch.await();

            for (FutureTask<StockDetailsData> futureTask : futureTaskList) {
                StockDetailsData stockDetailsData = futureTask.get(20, TimeUnit.MILLISECONDS);
                stockDetailsDataMap.put(stockDetailsData.getStockCode(), stockDetailsData);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        return stockDetailsDataMap;
    }
}
