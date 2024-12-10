package com.person.lsj.stock.bean.dongfang.task;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowDeserializer;
import com.person.lsj.stock.service.RedisOpsService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class GetStockCodeTodayMoneyData implements Callable<StockMoneyFlowBean> {
    private static Logger LOGGER = Logger.getLogger(GetStockCodeV6Details.class);
    private static final String REDIS_PREFIX_STOCK_MONEY_DATA = "STOCK_MONEY_TODAY_DATA";

    private String curStockCode;
    private HttpGet httpGet;
    private CountDownLatch countDownLatch;
    private RedisOpsService redisOpsService;
    private boolean useCache = false;

    public GetStockCodeTodayMoneyData(String curStockCode, HttpGet httpGet, CountDownLatch countDownLatch, RedisOpsService redisOpsService, boolean useCache) {
        this.curStockCode = curStockCode;
        this.httpGet = httpGet;
        this.countDownLatch = countDownLatch;
        this.redisOpsService = redisOpsService;
        this.useCache = useCache;
    }

    public GetStockCodeTodayMoneyData(String curStockCode, HttpGet httpGet, CountDownLatch countDownLatch) {
        this(curStockCode, httpGet, countDownLatch, null, false);
    }


    @Override
    public StockMoneyFlowBean call() throws Exception {
        LOGGER.debug("start get money flow data:" + curStockCode);
        StockMoneyFlowBean stockMoneyFlowBean = null;

        try {
            if (useCache && redisOpsService != null) {
                String stockMoneyFlowBeanJson = redisOpsService.getMapValue(REDIS_PREFIX_STOCK_MONEY_DATA, curStockCode);
                if (stockMoneyFlowBeanJson != null) {
                    stockMoneyFlowBean = JSON.parseObject(stockMoneyFlowBeanJson, StockMoneyFlowBean.class);
                    return stockMoneyFlowBean;
                }
            }

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
                stockMoneyFlowBean = mapper.readValue(buffer.toString(), StockMoneyFlowBean.class);
                if (useCache && redisOpsService != null && stockMoneyFlowBean != null) {
                    redisOpsService.setMapValue(REDIS_PREFIX_STOCK_MONEY_DATA, curStockCode, JSON.toJSONString(stockMoneyFlowBean));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            countDownLatch.countDown();
        }
        LOGGER.debug("end get money flow data:" + curStockCode);
        return stockMoneyFlowBean;
    }
}
