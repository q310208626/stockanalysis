package com.person.lsj.stock.bean.dongfang.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsDeserializer;
import com.person.lsj.stock.service.RedisOpsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class GetStockCodeName implements Callable<StockDetailsData> {
    private static Logger LOGGER = Logger.getLogger(GetStockCodeV6Details.class);

    private static final String STOCK_NAME_PREFIX = "STOCK_NAME:";

    private String curStockCode;
    private HttpGet httpGet;
    private CountDownLatch countDownLatch;
    private RedisOpsService redisOpsService;

    public GetStockCodeName(String curStockCode, HttpGet httpGet, CountDownLatch countDownLatch, RedisOpsService redisOpsService) {
        this.curStockCode = curStockCode;
        this.httpGet = httpGet;
        this.countDownLatch = countDownLatch;
        this.redisOpsService = redisOpsService;
    }

    @Override
    public StockDetailsData call() throws Exception {
        LOGGER.debug("Start getStockV6Detail:" + curStockCode);
        StockDetailsData stockDetailsData = null;

        try {
            String stockName = redisOpsService.getValue(STOCK_NAME_PREFIX + curStockCode);
            if (StringUtils.isNotEmpty(stockName)) {
                stockDetailsData = new StockDetailsData();
                stockDetailsData.setStockCode(curStockCode);
                stockDetailsData.setStockName(stockName);
                return stockDetailsData;
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
                redisOpsService.setValue(STOCK_NAME_PREFIX + stockDetailsData.getStockCode(), stockDetailsData.getStockName());
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
        LOGGER.debug("End getStockV6Detail:" + curStockCode);
        return stockDetailsData;
    }
}
