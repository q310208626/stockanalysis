package com.person.lsj.stock.bean.dongfang.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.person.lsj.stock.bean.dongfang.data.StockCurDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockCurDetailsDeserializer;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsDeserializer;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class GetStockCodeCurDayDetails implements Callable<StockCurDetailsData> {
    private static Logger LOGGER = Logger.getLogger(GetStockCodeV6Details.class);

    private String curStockCode;
    private HttpGet httpGet;
    private CountDownLatch countDownLatch;

    public GetStockCodeCurDayDetails(String curStockCode, HttpGet httpGet, CountDownLatch countDownLatch) {
        this.curStockCode = curStockCode;
        this.httpGet = httpGet;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public StockCurDetailsData call() throws Exception {
        LOGGER.debug("Start GetStockCodeCurDayDetails:" + curStockCode);
        StockCurDetailsData stockCurDetailsData = null;
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
            module.addDeserializer(StockCurDetailsData.class, new StockCurDetailsDeserializer());
            mapper.registerModule(module);
            stockCurDetailsData = mapper.readValue(buffer.toString(), StockCurDetailsData.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
        LOGGER.debug("End GetStockCodeCurDayDetails:" + curStockCode);
        return stockCurDetailsData;
    }
}
