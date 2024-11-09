package com.person.lsj.stock.service;


import com.person.lsj.stock.bean.dongfang.data.StockBoardBean;
import com.person.lsj.stock.bean.dongfang.data.StockCurDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface StockDataCapturerService {
    String getToken();

    int getTotalPage();

    /**
     * 获取所有的stockCode
     *
     * @return
     */
    List<String> getAllStockCodes();

    /**
     * 获取某页的stockCode
     *
     * @param page
     * @return
     */
    List<String> getStockCode(int page);

    /**
     * 获取3天内资金上升的stockCode
     *
     * @param stockCodes
     * @return List stockCode
     */
    List<String> getStockMoneyFlowUp(List<String> stockCodes);

    /**
     * 获取资金数据
     *
     * @param stockCodes
     * @return List stockCode
     */
    List<StockMoneyFlowBean> getStockMoneyFlowData(List<String> stockCodes);

    /**
     * 判断当前stockCode的v6曲线是否符合设置的标准
     *
     * @param stockCodes
     * @return List<String>
     */
    List<String> getStockV6Detail(List<String> stockCodes);

    /**
     * 获取stockCode列表对应的120天v6详情数据
     *
     * @param stockCodes
     * @return Map<String, StockDetailsData>
     */
    Map<String, StockDetailsData> getStockCodesV6Detail(List<String> stockCodes);

    /**
     * 获取stockCode列表对应的名称
     *
     * @param stockCodes
     * @return Map<String, String>
     */
    Map<String, String> getStockCodesNames(List<String> stockCodes);

    /**
     * 获取当前stockCode列表的详情数据
     *
     * @param stockCodes
     * @return
     */
    Map<String, StockCurDetailsData> getStockCodesCurDayDetail(List<String> stockCodes);

    /**
     * 用于设置昨天的任务数据
     *
     * @param stockDataResultDetailsList
     */
    void setStockResultDetails(List<StockDataResultDetails> stockDataResultDetailsList);

    /**
     * 获取上一个工作日
     *
     * @return
     */
    LocalDate getLastWorkDay();

    /**
     * 获取股票状态
     *
     * @param stockCode
     * @return status
     */
    Integer getStockStatus(String stockCode);

    /**
     * 获取板块总页数
     *
     * @param boardType 板块类型
     * @return
     */
    public int getBoardsTotalPage(int boardType);

    /**
     * 获取板块列表
     *
     * @param boardType 板块类型
     * @return List<StockBoardBean>
     */
    List<StockBoardBean> getAllStockBoards(int boardType);

    /**
     * 获取板块120天v6数据
     *
     * @param boardType 板块类型
     * @return Map<String, StockDetailsData>
     */
    Map<String, StockDetailsData> getStockBoardsV6Details(int boardType);
}
