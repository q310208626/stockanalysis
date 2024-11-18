package com.person.lsj.stock.filter.dongfang;

import com.person.lsj.stock.bean.dongfang.data.StockDataEntity;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.enumeration.TREND;
import com.person.lsj.stock.filter.StockDetailsDataFilter;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MdiStockDetailsDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(MdiStockDetailsDataFilter.class);

    private TREND[] pdiTrend = new TREND[0];
    private TREND[] mdiTrend = new TREND[0];
    private TREND[] adxTrend = new TREND[0];
    private float dValueBtwnPdiMdi = 10.0f;

    public MdiStockDetailsDataFilter(TREND[] pdiTrend, TREND[] mdiTrend, TREND[] adxTrend, float dValueBtwnPdiMdi) {
        this.pdiTrend = pdiTrend;
        this.mdiTrend = mdiTrend;
        this.adxTrend = adxTrend;
        this.dValueBtwnPdiMdi = dValueBtwnPdiMdi;
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
            int maxTrendDay = Math.max(Math.max(pdiTrend.length, mdiTrend.length), adxTrend.length);

            // at least one day judge dValue
            maxTrendDay = maxTrendDay == 0 ? 1 : maxTrendDay;
            int judgeDay = stockDetailsData.getStockDataEntities().size() <= maxTrendDay ? stockDetailsData.getStockDataEntities().size() - 1 : maxTrendDay;
            boolean matchJudgeRule = true;
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
                boolean pdiRet = filterPdi(stockDetailsData, curReverseDayNum);
                boolean mdiRet = filterMdi(stockDetailsData, curReverseDayNum);
                boolean adxRet = filterAdx(stockDetailsData, curReverseDayNum);
                matchJudgeRule = pdiRet && mdiRet && adxRet;

                // the last day kdjJ data must be keep within the target absolute value
                if (1 == curReverseDayNum && !(Math.abs(stockDataEntity.getDmiPdi() - stockDataEntity.getDmiMdi()) <= dValueBtwnPdiMdi)) {
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

    private boolean filterPdi(StockDetailsData stockDetailsData, int curReverseDayNum) {
        if (pdiTrend == null || pdiTrend.length == 0 || pdiTrend.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);
        TREND curDayJudgeRule = pdiTrend[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getDmiPdi() < stockDataEntity.getDmiPdi()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getDmiPdi() > stockDataEntity.getDmiPdi()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_RANDOM:
                break;
            default:
                break;
        }

        return matchJudgeRule;
    }

    private boolean filterMdi(StockDetailsData stockDetailsData, int curReverseDayNum) {
        if (mdiTrend == null || mdiTrend.length == 0 || mdiTrend.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);
        TREND curDayJudgeRule = mdiTrend[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getDmiMdi() < stockDataEntity.getDmiMdi()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getDmiMdi() > stockDataEntity.getDmiMdi()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_RANDOM:
                break;
            default:
                break;
        }
        return matchJudgeRule;
    }

    private boolean filterAdx(StockDetailsData stockDetailsData, int curReverseDayNum) {
        if ( adxTrend== null || adxTrend.length == 0 || adxTrend.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);
        TREND curDayJudgeRule = adxTrend[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getDmiAdx() < stockDataEntity.getDmiAdx()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getDmiAdx() > stockDataEntity.getDmiAdx()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_RANDOM:
                break;
            default:
                break;
        }
        return matchJudgeRule;
    }

    @Override
    public String getFilterRuleMsg() {
        StringBuffer filterRuleMsg = new StringBuffer("Mdi判断:[");
        filterRuleMsg.append("pdi跟mdi绝对值不超过[").append(dValueBtwnPdiMdi).append("]");
        if (pdiTrend != null && pdiTrend.length > 0) {
            filterRuleMsg.append(",判断pdi趋势[").append(pdiTrend.length).append("]天");
            for (int i = 0; i < pdiTrend.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(pdiTrend[i].tendString)
                        .append("趋势]");
            }
        }

        if (mdiTrend != null && mdiTrend.length > 0) {
            filterRuleMsg.append(",判断mdi趋势[").append(mdiTrend.length).append("]天");
            for (int i = 0; i < mdiTrend.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(mdiTrend[i].tendString)
                        .append("趋势]");
            }
        }

        if (adxTrend != null && adxTrend.length > 0) {
            filterRuleMsg.append(",判断adx趋势[").append(adxTrend.length).append("]天");
            for (int i = 0; i < adxTrend.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(adxTrend[i].tendString)
                        .append("趋势]");
            }
        }
        return filterRuleMsg.toString();
    }
}
