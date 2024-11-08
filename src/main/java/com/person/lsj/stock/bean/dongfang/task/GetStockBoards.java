package com.person.lsj.stock.bean.dongfang.task;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.person.lsj.stock.bean.dongfang.data.StockBoardBean;
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
import java.util.List;
import java.util.concurrent.Callable;

public class GetStockBoards implements Callable<List<StockBoardBean>> {
    private static Logger LOGGER = Logger.getLogger(GetStockBoards.class);

    private static final String STOCK_HOST = "https://16.push2.eastmoney.com";

    // get StockCodeList
    private static final String HOME_PAGE_URL = STOCK_HOST + "/api/qt/clist/get";

    private static int PAGE_SIZE = 100;

    @Override
    public List<StockBoardBean> call() throws Exception {
        LOGGER.debug("Start get StockBoardBeans");
        List<StockBoardBean> stockBoardBeans = new ArrayList<>();
        String curUrl = String.format(HOME_PAGE_URL);
        URIBuilder builder = new URIBuilder(curUrl);


        // pageSize
        builder.addParameter("pz", String.valueOf(PAGE_SIZE));

        // sotck board 1=shanghai
        builder.addParameter("po", "1");
        builder.addParameter("np", "1");
        builder.addParameter("fltt", "2");
        builder.addParameter("invt", "2");
        builder.addParameter("dect", "1");
        builder.addParameter("wbp2u", "|0|0|0|web");
        builder.addParameter("fid", "f3");
        builder.addParameter("fs", "m:90+t:2+f:!50");

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
            JSONArray diffJsonArray = dataJsonObject.getJSONArray("diff");
            for (int i = 0; i < diffJsonArray.size(); i++) {
                JSONObject jsonObject1 = diffJsonArray.getJSONObject(i);
                StockBoardBean stockBoardBean = jsonObject1.toJavaObject(StockBoardBean.class);
                stockBoardBeans.add(stockBoardBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("End get StockBoardBeans");
        return stockBoardBeans;
    }
}
