package com.person.lsj.stock.bean.dongfang.task;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsDeserializer;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.service.RedisOpsService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class GetStockCodeV6Details implements Callable<StockDetailsData> {
    private static Logger LOGGER = Logger.getLogger(GetStockCodeV6Details.class);
    private static final String REDIS_PREFIX_STOCK_DETAILS_DATA = "STOCK_DETAILS_DATA";

    private String curStockCode;
    private HttpGet httpGet;
    private CountDownLatch countDownLatch;
    private RedisOpsService redisOpsService;
    private boolean useCache = false;

    public GetStockCodeV6Details(String curStockCode, HttpGet httpGet, CountDownLatch countDownLatch, RedisOpsService redisOpsService, boolean useCache) {
        this.curStockCode = curStockCode;
        this.httpGet = httpGet;
        this.countDownLatch = countDownLatch;
        this.redisOpsService = redisOpsService;
        this.useCache = useCache;
    }

    public GetStockCodeV6Details(String curStockCode, HttpGet httpGet, CountDownLatch countDownLatch) {
        this.curStockCode = curStockCode;
    }

    @Override
    public StockDetailsData call() throws Exception {
        LOGGER.debug("Start getStockV6Detail:" + curStockCode);
        StockDetailsData stockDetailsData = null;

        try {
            if (useCache && redisOpsService != null) {
                String stockDetailsDataJson = redisOpsService.getMapValue(REDIS_PREFIX_STOCK_DETAILS_DATA, curStockCode);
                if (stockDetailsDataJson != null) {
                    stockDetailsData = JSON.parseObject(stockDetailsDataJson, StockDetailsData.class);
                    return stockDetailsData;
                }
            }

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                 InputStream inputStream = httpResponse.getEntity().getContent();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addDeserializer(StockDetailsData.class, new StockDetailsDeserializer());
                mapper.registerModule(module);
                stockDetailsData = mapper.readValue(buffer.toString(), StockDetailsData.class);
                if (useCache && redisOpsService != null && stockDetailsData != null) {
                    redisOpsService.setMapValue(REDIS_PREFIX_STOCK_DETAILS_DATA, curStockCode, JSON.toJSONString(stockDetailsData));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            countDownLatch.countDown();
        }
        LOGGER.debug("End getStockV6Detail:" + curStockCode);
        return stockDetailsData;
    }
}
