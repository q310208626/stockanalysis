package com.person.lsj.stock.bean.dongfang.data;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockDetailsDeserializer extends JsonDeserializer<StockDetailsData> {
    @Override
    public StockDetailsData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        StockDetailsData stockDetailsData = new StockDetailsData();

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode dataNode = jsonNode.get("data");

        if (dataNode == null) {
            return null;
        }

        // stockCode
        if (dataNode.get("code") != null) {
            String stockCode = dataNode.get("code").asText();
            stockDetailsData.setStockCode(stockCode);
        }

        if (dataNode.get("name") != null) {
            String stockName = dataNode.get("name").asText();
            stockDetailsData.setStockName(stockName);
        }

        if (dataNode.get("klines") != null) {
            List<StockDataEntity> stockDataEntityList = new ArrayList<>();
            JsonNode klinesObject = dataNode.get("klines");
            for (int i = 0; i < klinesObject.size(); i++) {
                String klineData = klinesObject.get(i).asText();
                StockDataEntity stockDataEntity = new StockDataEntity(klineData);
                stockDataEntityList.add(stockDataEntity);
            }
            stockDetailsData.setStockDataEntities(stockDataEntityList);
        }

        return stockDetailsData;
    }
}
