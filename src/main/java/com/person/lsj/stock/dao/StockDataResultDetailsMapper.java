package com.person.lsj.stock.dao;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockDataResultDetailsMapper {

    public void addBatch(List<StockDataResultDetails> stockDataResultDetailsList);
    public void add(StockDataResultDetails stockDataResultDetails);
    public void update(StockDataResultDetails stockDataResultDetails);
    public void updateBatch(List<StockDataResultDetails> stockDataResultDetails);
    public StockDataResultDetails query(int detailsId);
    public List<StockDataResultDetails> queryBatch(List<Integer> detailsIds);
}
