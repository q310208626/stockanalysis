package com.person.lsj.stock.bean.dongfang.moneyflow;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StockMoneyFlowDeserializer extends JsonDeserializer<StockMoneyFlowBean> {
    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    @Override
    public StockMoneyFlowBean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode dataNode = jsonNode.get("data");

        StockMoneyFlowBean stockMoneyFlowBean = new StockMoneyFlowBean();

        if (dataNode == null) {
            return null;
        }

        // stockCode
        if (dataNode.get("code") != null) {
            String stockCode = dataNode.get("code").asText();
            stockMoneyFlowBean.setStockCode(stockCode);
        }

        // klineData
        if (dataNode.get("klines") != null) {
            List<StockMoneyFlowData> datas = new ArrayList<>();
            JsonNode klinesObject = dataNode.get("klines");
            for (int i = 0; i < klinesObject.size(); i++) {
                StockMoneyFlowData stockMoneyFlowData = new StockMoneyFlowData();
                String klineStr = klinesObject.get(i).asText();
                String[] klineDatas = klineStr.split(",");
                String dataTimeStr = klineDatas[0];
                String leadMoney = klineDatas[1];
                String largeOrderMoney = klineDatas[4];
                String superLargeOrderMoney = klineDatas[5];

                try {
                    Date dataDate = dateFormat.get().parse(dataTimeStr);
                    stockMoneyFlowData.setDataDate(dataDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                stockMoneyFlowData.setMainMoneyFlow(Float.valueOf(leadMoney));
                stockMoneyFlowData.setLargeOrderMoney(Float.valueOf(largeOrderMoney));
                stockMoneyFlowData.setSuperLargeOrderMoney(Float.valueOf(superLargeOrderMoney));
                datas.add(stockMoneyFlowData);
            }
            Collections.sort(datas, new StockMoneyFlowDataComparator());
            stockMoneyFlowBean.setDatas(datas);
        }

        return stockMoneyFlowBean;
    }

    class StockMoneyFlowDataComparator implements Comparator<StockMoneyFlowData> {

        @Override
        public int compare(StockMoneyFlowData o1, StockMoneyFlowData o2) {
            if (o1.getDataDate().before(o2.getDataDate())) {
                return 1;
            }
            return -1;
        }
    }
}
