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

    @Bean(name = "stockDetailsDataFilterChain3DaysMoneyFlow3000wLD")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain4() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_DOWN, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(new ArrayList<>());
        stockDetailsDataFilterChain.setTaskId("Task_0003_资金减持3000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainBollDvalue0015AndMainFlow")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain5() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);
        StockDetailsDataFilter bollStockDetailsDataFilter = new BollStockDetailsDataFilter();
        List<StockDetailsDataFilter> filters = new ArrayList<>();
        filters.add(bollStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        stockDetailsDataFilterChain.setTaskId("Task_0004_boll0015");
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainBollDvalue002AndMainFlow")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain6() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);
        StockDetailsDataFilter bollStockDetailsDataFilter = new BollStockDetailsDataFilter(0.02f);
        List<StockDetailsDataFilter> filters = new ArrayList<>();
        filters.add(bollStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        stockDetailsDataFilterChain.setTaskId("Task_0005_boll0020");
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
        stockDetailsDataFilterChain.setTaskId("Task_0007_追涨");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJUp2")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJUp2() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_DOWN);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(15, new TREND[]{TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0008_底部背离");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChainKDJUp3")
    public StockDetailsDataFilterChain stockDetailsDataFilterChainKDJUp3() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();

        MacdStockDetailsDataFilter macdStockDetailsDataFilter = new MacdStockDetailsDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN, TREND.TEND_DOWN}, TREND.TEND_DOWN);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(30, new TREND[]{TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        filters.add(macdStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0009_底部背离");
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
        stockDetailsDataFilterChain.setTaskId("Task_0011_金叉判断");
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

    @Bean(name = "stockDetailsDataFilterChain_VolumeMulti_15UP")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_VolumeMulti_15UP() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        VolumeDetailsFilter volumeDetailsFilter = new VolumeDetailsFilter();
        filters.add(increaseRateDataFilter);
        filters.add(volumeDetailsFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0015_交易量为昨天的1.5倍");
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

    @Bean(name = "stockDetailsDataFilterChain_BigOrder_2UP_LowIncrRate_Kdj2Up")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_BigOrder_2UP_LowIncrRate_Kdj2Up() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});

        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        filters.add(increaseRateDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0017_主力2天投入资金涨幅不大_Kdj两次上升");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_BigOrder_2UP_MdiUp_KD_dValue")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_BigOrder_2UP_MdiUp_KD_dValue() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP});

        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(8.0f, new TREND[]{TREND.TEND_UP}, new TREND[]{TREND.TEND_DOWN});
        filters.add(increaseRateDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0018_主力2天投入资金涨幅不大_KD金叉");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_BigOrder_2UP_MdiUp_KD_dValue_Money3000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_BigOrder_2UP_MdiUp_KD_dValue_Money3000() {
        BigOrderMoneyFlowDataFilter moneyFlowDataFilter = new BigOrderMoneyFlowDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, new TREND[]{TREND.TEND_UP, TREND.TEND_UP}, 30000000);

        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(8.0f, new TREND[]{TREND.TEND_UP}, new TREND[]{TREND.TEND_DOWN});
        filters.add(increaseRateDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0019_主力2天投入资金涨幅不大_KD金叉_资金流入3000万");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_VolumeMulti_18UP")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_VolumeMulti_18UP() {
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter();
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        VolumeDetailsFilter volumeDetailsFilter = new VolumeDetailsFilter(1.8f);
        filters.add(increaseRateDataFilter);
        filters.add(volumeDetailsFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0020_交易量为昨天的1.8倍");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_VolumeMulti_20UP_Money3000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_VolumeMulti_20UP_Money3000() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);
        List<StockDetailsDataFilter> filters = new ArrayList<StockDetailsDataFilter>();
        IncreaseRateDataFilter increaseRateDataFilter = new IncreaseRateDataFilter(new TREND[]{TREND.TEND_DOWN, TREND.TEND_DOWN}, new float[]{3.0f, 3.0f});
        VolumeDetailsFilter volumeDetailsFilter = new VolumeDetailsFilter(2.0f);
        filters.add(increaseRateDataFilter);
        filters.add(volumeDetailsFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0021_交易量为昨天的2倍_资金流入3000万");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain_BollDvalue002_Money3000")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain_BollDvalue002_Money3000() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);
        StockDetailsDataFilter bollStockDetailsDataFilter = new BollStockDetailsDataFilter(0.02f);
        KdjStockDetailsDataFilter kdjStockDetailsDataFilter = new KdjStockDetailsDataFilter(new TREND[]{TREND.TEND_UP, TREND.TEND_UP});
        List<StockDetailsDataFilter> filters = new ArrayList<>();
        filters.add(bollStockDetailsDataFilter);
        filters.add(kdjStockDetailsDataFilter);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(filters);
        stockDetailsDataFilterChain.setTaskId("Task_0022_boll&kdj_资金流入3000万");
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
}
