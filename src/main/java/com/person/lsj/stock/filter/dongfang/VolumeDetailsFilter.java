package com.person.lsj.stock.filter.dongfang;

import com.person.lsj.stock.bean.dongfang.data.StockDataEntity;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.filter.StockDetailsDataFilter;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VolumeDetailsFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(VolumeDetailsFilter.class);

    private static float DEFAULT_MULTI_RATIO = 1.5f;

    private float multiRatio = 1.5f;

    public VolumeDetailsFilter() {
        this.multiRatio = DEFAULT_MULTI_RATIO;
    }

    public VolumeDetailsFilter(float multiRatio) {
        this.multiRatio = multiRatio;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if (CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int judgeDay = 1;
            boolean matchJudgeRule = true;
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
                StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);

                float volume = stockDataEntity.getVolume();
                float volumePre = stockDataEntityPre.getVolume();
                // the last day kdjJ data must be keep within the target absolute value
                if (volume / volumePre < multiRatio) {
                    matchJudgeRule = false;
                }

                // if one days data no match,break down current loop
                if (!matchJudgeRule) {
                    break;
                }
            }

            // if match,add into result
            if (matchJudgeRule) {
                result.put(stockCode, stockDetailsData);
            }

        }
        LOGGER.debug("exit filter,size:" + result.size());
        return result;
    }

    @Override
    public String getFilterRuleMsg() {
        StringBuffer filterRuleMsg = new StringBuffer("交易量判断: ");
        filterRuleMsg.append("当天交易量是前一天的[").append(multiRatio).append("]倍");
        return filterRuleMsg.toString();
    }
}
