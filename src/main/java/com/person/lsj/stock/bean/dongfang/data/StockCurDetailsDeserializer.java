package com.person.lsj.stock.bean.dongfang.data;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class StockCurDetailsDeserializer extends JsonDeserializer<StockCurDetailsData> {
    private static final String CUR_PRICE = "f43";
    private static final String HIGH_PRICE = "f44";
    private static final String LOW_PRICE = "f45";
    private static final String OPEN_PRICE = "f46";
    private static final String VOLUME = "f47";
    private static final String STOCKCODE = "f57";
    private static final String STOCKNAME = "f58";
    private static final String INCREASE_PERCENTAGE = "f170";

    @Override
    public StockCurDetailsData deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        StockCurDetailsData stockCurDetailsData = new StockCurDetailsData();

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode dataNode = jsonNode.get("data");

        if (dataNode == null) {
            return null;
        }

        // stockCode
        if (dataNode.get(STOCKCODE) != null) {
            String stockCode = dataNode.get(STOCKCODE).asText();
            stockCurDetailsData.setStockCode(stockCode);
        }

        if (dataNode.get(STOCKNAME) != null) {
            String stockName = dataNode.get(STOCKNAME).asText();
            stockCurDetailsData.setStockName(stockName);
        }

        if (dataNode.get(CUR_PRICE) != null) {
            String curPrice = dataNode.get(CUR_PRICE).asText();
            stockCurDetailsData.setCur(Float.valueOf(curPrice));
        }

        if (dataNode.get(HIGH_PRICE) != null) {
            String highPrice = dataNode.get(HIGH_PRICE).asText();
            stockCurDetailsData.setHigh(Float.valueOf(highPrice));
        }

        if (dataNode.get(LOW_PRICE) != null) {
            String lowPrice = dataNode.get(LOW_PRICE).asText();
            stockCurDetailsData.setLow(Float.valueOf(lowPrice));
        }

        if (dataNode.get(OPEN_PRICE) != null) {
            String openPrice = dataNode.get(OPEN_PRICE).asText();
            stockCurDetailsData.setOpen(Float.valueOf(openPrice));
        }

        if (dataNode.get(VOLUME) != null) {
            String volume = dataNode.get(VOLUME).asText();
            stockCurDetailsData.setVolume(Integer.valueOf(volume));
        }

        if (dataNode.get(INCREASE_PERCENTAGE) != null) {
            String increasePercentage = dataNode.get(INCREASE_PERCENTAGE).asText();
            stockCurDetailsData.setIncreasePercentage(Integer.valueOf(increasePercentage));
        }

        return stockCurDetailsData;
    }
}
