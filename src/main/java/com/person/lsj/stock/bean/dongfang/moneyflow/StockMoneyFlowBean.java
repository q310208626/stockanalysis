package com.person.lsj.stock.bean.dongfang.moneyflow;

import java.util.List;

public class StockMoneyFlowBean {
    private String stockCode;
    private List<StockMoneyFlowData> datas;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public List<StockMoneyFlowData> getDatas() {
        return datas;
    }

    public void setDatas(List<StockMoneyFlowData> datas) {
        this.datas = datas;
    }
}
