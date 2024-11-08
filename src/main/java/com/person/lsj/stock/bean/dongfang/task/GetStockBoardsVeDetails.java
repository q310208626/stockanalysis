package com.person.lsj.stock.bean.dongfang.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
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

public class GetStockBoardsVeDetails implements Callable<StockDetailsData> {
    private static Logger LOGGER = Logger.getLogger(GetStockCodeV6Details.class);

    private String boardCokde;
    private HttpGet httpGet;
    private CountDownLatch countDownLatch;

    public GetStockBoardsVeDetails(String boardCokde, HttpGet httpGet, CountDownLatch countDownLatch) {
        this.boardCokde = boardCokde;
        this.httpGet = httpGet;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public StockDetailsData call() throws Exception {
        LOGGER.debug("Start getStockBoardV6Detail:" + boardCokde);
        StockDetailsData stockDetailsData = null;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
        LOGGER.debug("End getStockBoardV6Detail:" + boardCokde);
        return stockDetailsData;
    }
}
