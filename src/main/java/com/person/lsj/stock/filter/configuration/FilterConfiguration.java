package com.person.lsj.stock.filter.configuration;

import com.person.lsj.stock.enumeration.TREND;
import com.person.lsj.stock.filter.StockDetailsDataFilter;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.filter.dongfang.BollStockDetailsDataFilter;
import com.person.lsj.stock.filter.dongfang.KdjStockDetailsDataFilter;
import com.person.lsj.stock.filter.dongfang.MacdStockDetailsDataFilter;
import com.person.lsj.stock.filter.dongfang.MainMoneyFlowDataFilter;
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
        stockDetailsDataFilterChain.setTaskId("Task_0000");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain3DaysMoneyFlow3000w")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain2() {
        // 3 day`s main money flow are all increased
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(new ArrayList<>());
        stockDetailsDataFilterChain.setTaskId("Task_0001");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain3DaysMoneyFlow5000w")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain3() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_UP, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 50000000);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(new ArrayList<>());
        stockDetailsDataFilterChain.setTaskId("Task_0002");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }

    @Bean(name = "stockDetailsDataFilterChain3DaysMoneyFlow3000wLD")
    public StockDetailsDataFilterChain stockDetailsDataFilterChain4() {
        TREND[] judgeRule = new TREND[]{TREND.TEND_DOWN, TREND.TEND_UP, TREND.TEND_UP};
        MainMoneyFlowDataFilter moneyFlowDataFilter = new MainMoneyFlowDataFilter(judgeRule, 30000000);

        StockDetailsDataFilterChain stockDetailsDataFilterChain = new StockDetailsDataFilterChain(new ArrayList<>());
        stockDetailsDataFilterChain.setTaskId("Task_0003");
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
        stockDetailsDataFilterChain.setTaskId("Task_0004");
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
        stockDetailsDataFilterChain.setTaskId("Task_0005");
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
        stockDetailsDataFilterChain.setTaskId("Task_0006");
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
        stockDetailsDataFilterChain.setTaskId("Task_0007");
        stockDetailsDataFilterChain.setMoneyFlowDataFilter(moneyFlowDataFilter);
        return stockDetailsDataFilterChain;
    }
}
