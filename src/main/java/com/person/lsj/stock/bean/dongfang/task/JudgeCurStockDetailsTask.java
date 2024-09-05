package com.person.lsj.stock.bean.dongfang.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.person.lsj.stock.bean.dongfang.data.StockDataEntity;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsDeserializer;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JudgeCurStockDetailsTask implements Runnable {
    private static Logger LOGGER = Logger.getLogger(JudgeCurStockDetailsTask.class);

    // abs(kdj) <= 10
    private static int TARGET_ABS_KDJ = 15;


    private List<String> detailsGoodStockCodes;
    private String curStockCode;
    private HttpGet httpGet;

    public JudgeCurStockDetailsTask(List<String> detailsGoodStockCodes, String curStockCode, HttpGet httpGet) {
        this.detailsGoodStockCodes = detailsGoodStockCodes;
        this.curStockCode = curStockCode;
        this.httpGet = httpGet;
    }

    @Override
    public void run() {
        LOGGER.debug("start judge stock details:" + curStockCode);
        judgeCurStockDetails(detailsGoodStockCodes, curStockCode, httpGet);
        LOGGER.debug("end judge stock details:" + curStockCode);
    }

    // 判断指标
    private void judgeCurStockDetails(List<String> detailsGoodStockCodes, String stockCode, HttpGet httpGet) {
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
            module.addDeserializer(StockDetailsData.class,new StockDetailsDeserializer());
            mapper.registerModule(module);
            StockDetailsData stockDetailsData = mapper.readValue(buffer.toString(), StockDetailsData.class);

            // MACD判断
            int judgeDay = 4;
            judgeDay = stockDetailsData.getStockDataEntities().size() > judgeDay ? judgeDay : stockDetailsData.getStockDataEntities().size() - 1;
            for (int i = 0; i < judgeDay - 1; i++) {
                StockDataEntity stockDataEntityMacd = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - (judgeDay - i));
                StockDataEntity stockDataEntityMacdNext = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - (judgeDay - 1 - i));
                // mackdiff不是上升趋势
                if (stockDataEntityMacd.getMacd() > 0 || stockDataEntityMacdNext.getMacd() < stockDataEntityMacd.getMacd()) {
                    return;
                }
            }

            // KDJ判断，向前判断5天
            judgeDay = 4;
            judgeDay = stockDetailsData.getStockDataEntities().size() > judgeDay ? judgeDay : stockDetailsData.getStockDataEntities().size() - 1;
            for (int i = 0; i < judgeDay; i++) {
                StockDataEntity stockDataEntityKdj = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - (judgeDay - i));
                if (i == judgeDay - 1) {
                    if (Math.abs(stockDataEntityKdj.getKdjJ()) <= TARGET_ABS_KDJ) {
                        detailsGoodStockCodes.add(stockCode);
                    }
                }
//                else {
//                    StockDataEntity stockDataEntityKdjNext = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - (judgeDay - 1 - i));
////                        if (Math.abs(stockDataEntityKdj.getKdjJ() - stockDataEntityKdjNext.getKdjJ()) > 5 && stockDataEntityKdj.getKdjJ() < stockDataEntityKdjNext.getKdjJ()) {
//                    if (stockDataEntityKdj.getKdjJ() > stockDataEntityKdjNext.getKdjJ()) {
//                        break;
//                    }
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(RuntimeException e){
            e.printStackTrace();
        }
    }
}
