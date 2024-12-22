package com.person.lsj.stock.configuration;

import com.person.lsj.stock.constant.Constant;
import com.person.lsj.stock.enumeration.TREND;
import com.person.lsj.stock.filter.StockDetailsDataFilter;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.filter.dongfang.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterConfiguration {

    @Bean(name = "stockDetailsDataFilterChain1")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain1() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0000_熊指标");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_KDJ_MACD_MainMoney3000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_KDJ_MACD_MainMoney3000() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_RANDOM, TREND.TEND_RANDOM}, 10000000);

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0000_KDJ_MACD_主力1000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_KDJ_MACD_BigOrder1000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_KDJ_MACD_BigOrder1000() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0000_KDJ_MACD_大单1000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_KDJ_MACD_BigOrder3000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_KDJ_MACD_BigOrder3000() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, 30000000);

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0000_KDJ_MACD_大单3000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_KDJ_MACD_Reverse")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_KDJ_MACD_Reverse() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_DOWN});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(15, new TREND[]{TREND.TEND_UP, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0000_KDJ_MACD_底部反转");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_KDJ_MACD_CCI")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_KDJ_MACD_CCI() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(25, new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        CciStockDetailsDataFilter cciStockDetailsDataFilter = new CciStockDetailsDataFilter(-70, -60, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);
        filters.add(cciStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0000_KDJ_MACD_CCI");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_KDJ_MACDLargeThanZero")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_KDJ_MACDLargeThanZero() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_RANDOM, TREND.TEND_RANDOM}, 5000000);

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_RANDOM, TREND.TEND_RANDOM}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, TREND.TEND_RANDOM, TREND.TEND_UP);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(20, 50, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        CciStockDetailsDataFilter cciStockDetailsDataFilter = new CciStockDetailsDataFilter(20, 80, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);
        filters.add(cciStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0000_KDJ_MACDLargeThanZero");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain3DaysMoneyFlow3000w")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain2() {
        // 3 day`s main money flow are all increased
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(new ArrayList<>());
        stockDetailsDataFilterChain.setTaskId("Task_0001_资金连涨3000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain3DaysMoneyFlow5000w")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain3() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 50000000);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(new ArrayList<>());
        stockDetailsDataFilterChain.setTaskId("Task_0002_资金连涨5000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainBollDvalue002")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain7() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        StockDetailsDataFilter bollStockDetailsDataFilter = new BollStockDetailsDataFilter(0.02f);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<>();
        filters.add(bollStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0006_boll&kdj");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJUp")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJUp() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP}, TREND.TEND_RANDOM);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(80, new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0007_追涨_KDJ上涨3次70_主力1000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJUp3_70_BigOrder1000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJUp3_70_BigOrder1000() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP}, TREND.TEND_RANDOM);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(70, new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0007_追涨_KDJ上涨3次70_大单1000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJUp3_60_BigOrder1000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJUp3_60_BigOrder1000() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP}, TREND.TEND_RANDOM);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(60, new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0007_追涨_KDJ上涨3次60_大单1000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJUp")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJUp2() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_DOWN);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(25, new TREND[]{TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0008_底部背离");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJUp4")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJUp4() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_RANDOM);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(30, new TREND[]{TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0010_盘中震荡");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainMdiUp")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainMdiUp() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP}, 10000000);

        TREND[] pdiTrend = new TREND[]{TREND.TEND_UP};
        TREND[] mdiTrend = new TREND[]{TREND.TEND_DOWN};
        TREND[] adxTrend = new TREND[]{TREND.TEND_UP};
        float dValueBtwnPdiMdi = 15.0f;
        MdiStockDetailsDataFilter mdiStockDetailsDataFilter = new MdiStockDetailsDataFilter(pdiTrend, mdiTrend, adxTrend, dValueBtwnPdiMdi);
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(mdiStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0011_mdi金叉判断");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJ_KD_dValue")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJ_KD_dValue() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(8.0f, new TREND[]{TREND.TEND_UP}, new TREND[]{TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0012_KD金叉");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJ_K2Down1Up_D3Down_dValue10_BigOrder1000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJ_K2Down1Up_D3Down_dValue10_BigOrder1000() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});

        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(10.0f, new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_UP}, new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0012_KD金叉_大订单2天");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJ_K2Down1Up_D3Down_dValue10_BigOrder1Up1000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJ_K2Down1Up_D3Down_dValue10_BigOrder1Up1000() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(20, new TREND[]{TREND.TEND_UP});
        RsiStockDetailsDataFilter rsiStockDetailsDataFilter = new RsiStockDetailsDataFilter(30, 40, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(kdjStockDetailsDataFilter);
        filters.add(rsiStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0012_KDJ20_Rsi2连升");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJ_KD_3Down_dValue")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJ_KD_3Down_dValue() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(20.0f, new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN}, new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0013_KD_3连降");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_BigOrder_2UP_LowIncrRate")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_BigOrder_2UP_LowIncrRate() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});

        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        filters.add(increaseRateDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0014_主力2天投入资金涨幅不大");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_VolumeMulti_20UP")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_VolumeMulti_20UP() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        VolumeDetailsFilter volumeDetailsFilter = new VolumeDetailsFilter(2.0f);
        filters.add(increaseRateDataFilter);
        filters.add(volumeDetailsFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0016_交易量为昨天的2倍");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_Volume3Up")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_Volume3Up() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{2f, 1.0f});
        VolumeTrendDetailsFilter volumeDetailsFilter = new VolumeTrendDetailsFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP}, true, false);
        filters.add(increaseRateDataFilter);
        filters.add(volumeDetailsFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0016_交易量3天上升_涨幅不大");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdJudgeFilterChain1")
    public StockDetailsDataFilterChain stockBoardMacdJudgeFilterChain1() {
        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_RANDOM);
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0001");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdJudgeFilterChain2")
    public StockDetailsDataFilterChain stockBoardMacdJudgeFilterChain2() {

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_DOWN}, TREND.TEND_RANDOM);
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0002");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdJudgeFilterChain3")
    public StockDetailsDataFilterChain stockBoardMacdJudgeFilterChain3() {

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, TREND.TEND_DOWN);
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0003");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdAndKdjJudgeFilterChain")
    public StockDetailsDataFilterChain stockBoardMacdAndKdjJudgeFilterChain() {
        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_DOWN);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(25, new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0004");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdAndKdjJudgeFilterChain2")
    public StockDetailsDataFilterChain stockBoardMacdAndKdjJudgeFilterChain2() {
        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_DOWN);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(50, new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0005");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdAndKdjJudgeFilterChain3")
    public StockDetailsDataFilterChain stockBoardMacdAndKdjJudgeFilterChain3() {
        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, TREND.TEND_DOWN);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(25, new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0006");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdAndKdjJudgeFilterChain4")
    public StockDetailsDataFilterChain stockBoardMacdAndKdjJudgeFilterChain4() {
        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, TREND.TEND_DOWN);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(50, new TREND[]{TREND.TEND_RANDOM, TREND.TEND_DOWN, TREND.TEND_DOWN});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0007");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMacdAndKdjJudgeFilterChain5")
    public StockDetailsDataFilterChain stockBoardMacdAndKdjJudgeFilterChain5() {
        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_RANDOM);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(60, new TREND[]{TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0008");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMainMoneyFlow2Up")
    public StockDetailsDataFilterChain stockBoardMainMoneyFlow2Up() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP,TREND.TEND_UP},300000000);
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0009_主力资金2天流入3亿");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockBoardMainMoneyFlow_FirstUp")
    public StockDetailsDataFilterChain stockBoardMainMoneyFlow_FirstUp() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_DOWN, TREND.TEND_DOWN}, 100000000);
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_boards_0009_主力资金_两次出逃后再加入");
        stockDetailsDataFilterChain.setFlag(Constant.TASK_FLAG_STOCK_BOARD);
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }
}
