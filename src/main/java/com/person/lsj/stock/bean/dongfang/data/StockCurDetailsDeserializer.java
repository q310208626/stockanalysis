package com.person.lsj.stock.bean.dongfang.data;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.DecimalFormat;

public class StockCurDetailsDeserializer extends JsonDeserializer<StockCurDetailsData> {
    private static Logger LOGGER = Logger.getLogger(StockCurDetailsDeserializer.class);
    private static final String CUR_PRICE = "f43";
    private static final String HIGH_PRICE = "f44";
    private static final String LOW_PRICE = "f45";
    private static final String OPEN_PRICE = "f46";
    private static final String VOLUME = "f47";
    private static final String STOCKCODE = "f57";
    private static final String STOCKNAME = "f58";
    private static final String INCREASE_PERCENTAGE = "f170";
    private static final String STOCK_STATUS = "f292";

    private DecimalFormat df = new DecimalFormat(".00");

    @Override
    public StockCurDetailsData deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException{
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
            try {
                String curPrice = dataNode.get(CUR_PRICE).asText();
                stockCurDetailsData.setCur(df.format(Float.valueOf(curPrice) / 100));
            } catch (NumberFormatException e) {
                stockCurDetailsData.setCur("0");
            }
        }

        if (dataNode.get(HIGH_PRICE) != null) {
            try {
                String highPrice = dataNode.get(HIGH_PRICE).asText();
                stockCurDetailsData.setHigh(df.format(Float.valueOf(highPrice) / 100));
            } catch (NumberFormatException e) {
                stockCurDetailsData.setHigh("0");
            }
        }

        if (dataNode.get(LOW_PRICE) != null) {
            try {
                String lowPrice = dataNode.get(LOW_PRICE).asText();
                stockCurDetailsData.setLow(df.format(Float.valueOf(lowPrice) / 100));
            } catch (NumberFormatException e) {
                stockCurDetailsData.setLow("0");
            }
        }

        if (dataNode.get(OPEN_PRICE) != null) {
            try {
                String openPrice = dataNode.get(OPEN_PRICE).asText();
                stockCurDetailsData.setOpen(df.format(Float.valueOf(openPrice) / 100));
            } catch (NumberFormatException e) {
                stockCurDetailsData.setOpen("0");
            }
        }

        if (dataNode.get(VOLUME) != null) {
            try {
                String volume = dataNode.get(VOLUME).asText();
                stockCurDetailsData.setVolume(Integer.valueOf(volume));
            } catch (NumberFormatException e) {
                stockCurDetailsData.setVolume(0);
            }
        }

        if (dataNode.get(INCREASE_PERCENTAGE) != null) {
            try {
                String increasePercentage = dataNode.get(INCREASE_PERCENTAGE).asText();
                stockCurDetailsData.setIncreasePercentage(df.format(Float.valueOf(increasePercentage) / 100));
            } catch (NumberFormatException e) {
                stockCurDetailsData.setIncreasePercentage("0");
            }
        }

        if (dataNode.get(STOCK_STATUS) != null) {
            String stockStatus = dataNode.get(STOCK_STATUS).asText();
            stockCurDetailsData.setStockStatus(Integer.valueOf(stockStatus));
        }

        return stockCurDetailsData;
    }
}
