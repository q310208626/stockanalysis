package com.person.lsj.stock.dao;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StockDataResultMapper {
    public void add(StockDataResultSum stockDataResult);

    public void addBatch(List<StockDataResultSum> stockDataResultList);

    public StockDataResultSum queryById(int resultId);

    public StockDataResultSum queryBytaskIdAndDate(StockDataResultSum stockDataResultSum);

    public List<StockDataResultSum> queryByDate(StockDataResultSum stockDataResultSum);

    public void updateOne(StockDataResultSum stockDataResultSum);

    public void updateBatch(List<StockDataResultSum> stockDataResultSumList);

    public List<LocalDate> queryCollectDateDesc();
}
