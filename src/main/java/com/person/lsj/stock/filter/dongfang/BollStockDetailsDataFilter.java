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

public class BollStockDetailsDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(KdjStockDetailsDataFilter.class);

    // abs(curprice - curBottomPrice) <= 0.02f
    private static float TARGET_ABS_D_VALUE = 0.015f;

    // middle value trend
    private static int TREND_UP = 0;

    private static int TREND_DOWN = 1;

    private static int TREND_STABLE = 2;

    private static int TREND_JUDGE_DAY = 3;

    private static int BOTTOM_LOWER = 0;

    private static int BOTTOM_MID = 1;

    private static float DEFAULT_EPSILON = 0.01f;

    // difference value between curprice and curBottom
    private float absDValue = TARGET_ABS_D_VALUE;

    // boll`s trend,cur value determinted curBottom
    private float curTrend = 1;

    // 0= lower 1= middle
    private int curBottom = 0;

    public BollStockDetailsDataFilter() {
        this(TARGET_ABS_D_VALUE);
    }

    public BollStockDetailsDataFilter(float absDValue) {
        this.absDValue = absDValue;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        if (CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> filterResult = new HashMap<String, StockDetailsData>();
        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int judgeDay = stockDetailsData.getStockDataEntities().size() <= TREND_JUDGE_DAY ? stockDetailsData.getStockDataEntities().size() - 1 : TREND_JUDGE_DAY;

            setTrendAndBottom(judgeDay, stockDetailsData);
            StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().getLast();
            float curPrice = stockDataEntity.getClose();
            float bottomPrice = curBottom == BOTTOM_MID ? stockDataEntity.getBoll() : stockDataEntity.getBollLower();
            if (Math.abs((curPrice - bottomPrice) / curPrice) <= absDValue) {
                filterResult.put(stockCode, stockDetailsData);
            }
        }
        return filterResult;
    }

    @Override
    public String getFilterRuleMsg() {
        StringBuffer filterRuleMsg = new StringBuffer("Boll判断: ");
        filterRuleMsg.append("判断[").append(TREND_JUDGE_DAY).append("]天, ");
        filterRuleMsg.append("根据趋势选择BollLower或者BollMid作为底, ");
        filterRuleMsg.append("当前值跟底线值的增长率的绝对值为[").append(absDValue).append("]");
        return filterRuleMsg.toString();
    }

    /**
     * @param judgeDay
     * @param stockDetailsData
     */
    private void setTrendAndBottom(int judgeDay, StockDetailsData stockDetailsData) {
        int trendNum = TREND_JUDGE_DAY;
        for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

            StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
            StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);

            float bollMid = stockDataEntity.getBoll();
            float bollMidPre = stockDataEntityPre.getBoll();

            if (isGreater(bollMid, bollMidPre, DEFAULT_EPSILON)) {
                trendNum++;
            } else {
                trendNum--;
            }
        }

        if (trendNum == TREND_JUDGE_DAY * 2) {
            curTrend = TREND_UP;
        } else if (trendNum == 0) {
            curTrend = TREND_DOWN;
        } else {
            curTrend = TREND_STABLE;
        }

        if (curTrend <= TREND_UP) {
            curBottom = BOTTOM_MID;
        } else {
            curBottom = BOTTOM_LOWER;
        }
    }
    public static boolean isGreater(float a, float b, float epsilon) {
        return a - b > epsilon;
    }

}
