package com.person.lsj.stock.bean.dongfang.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.lsj.stock.bean.dongfang.HomePageBean;
import com.person.lsj.stock.bean.dongfang.StockField;
import com.person.lsj.stock.constant.Constant;
import com.person.lsj.stock.constant.StockStatus;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class GetStockCodeList implements Callable<List<String>> {
    private static Logger LOGGER = Logger.getLogger(GetStockCodeV6Details.class);

    private static final String STOCK_HOST = "https://43.push2.eastmoney.com";

    // get StockCodeList
    private static final String HOME_PAGE_URL = STOCK_HOST + "/api/qt/clist/get";

    private int page;

    private CountDownLatch countDownLatch;

    public GetStockCodeList(int page, CountDownLatch countDownLatch) {
        this.page = page;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public List<String> call() throws Exception {
        LOGGER.debug("Start get StockCodeList page: " + page);
        List<String> stockCodeList = new ArrayList<>();
        String curUrl = String.format(HOME_PAGE_URL);
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
        builder.addParameter("fs", "m:1+t:2,m:1+t:23,m:0+t:6");

        // curPageNum
        builder.addParameter("pn", String.valueOf(page));

        // f292 状态 dealTradeStae 6停牌 7退市
        builder.addParameter("fields", "f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152,f292");
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

            ObjectMapper objectMapper = new ObjectMapper();
            HomePageBean homePageBean = objectMapper.readValue(dataBuffer.toString(), HomePageBean.class);
            StockField[] stockFields = homePageBean.getData() == null ? null : homePageBean.getData().getDiff();

            if (null != stockFields) {
                stockCodeList = Arrays.stream(stockFields)
                        .filter(x -> x.getStatus() != StockStatus.SUSPEND.status)
                        .filter(x -> x.getStatus() != StockStatus.DELISTED.status)
                        .filter(x -> x.getStatus() != StockStatus.SUSPEND_LISTING.status)
                        .filter(x -> x.getStatus() != StockStatus.NOT_LISTING.status)
//                        .filter(x -> x.getStatus() != StockStatus.CLOSED.status)
                        .filter(x -> {
                            if (x.getStockCode() == null) {
                                LOGGER.debug("This Status " + x.getStatus() + " has no stock code");
                                return false;
                            } else {
                                return true;
                            }
                        })
                        .map(x -> x.getStockCode()).collect(Collectors.toList());


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
        LOGGER.debug("End get StockCodeList page: " + page);
        return stockCodeList;
    }
}
