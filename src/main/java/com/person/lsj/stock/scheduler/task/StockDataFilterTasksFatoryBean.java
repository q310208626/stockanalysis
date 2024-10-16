package com.person.lsj.stock.scheduler.task;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StockDataFilterTasksFatoryBean implements FactoryBean<StockDataFilterTasks>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public StockDataFilterTasks getObject() throws Exception {
        String[] filterChainNames = applicationContext.getBeanNamesForType(StockDetailsDataFilterChain.class);

        StockDataFilterTasks stockDataFilterTasks = new StockDataFilterTasks();
        Map<String, StockDetailsDataFilterChain> stockDetailsDataFilterChainMap = new HashMap<>();
        Map<String, StockDataResultSum> stockFilterTasksResultMap = new HashMap<>();

        if (ArrayUtils.isNotEmpty(filterChainNames)) {
            for (int i = 0; i < filterChainNames.length; i++) {
                String filterChainName = filterChainNames[i];
                StockDetailsDataFilterChain filterChainBean = applicationContext.getBean(filterChainName, StockDetailsDataFilterChain.class);
                stockDetailsDataFilterChainMap.put(filterChainBean.getTaskId(), filterChainBean);
            }
        }
        stockDataFilterTasks.setStockFilterTasksMap(stockDetailsDataFilterChainMap);
        stockDataFilterTasks.setStockFilterTasksResultMap(stockFilterTasksResultMap);

        return stockDataFilterTasks;
    }

    @Override
    public Class<?> getObjectType() {
        return StockDataFilterTasks.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
