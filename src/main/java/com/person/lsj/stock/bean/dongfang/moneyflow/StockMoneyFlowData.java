package com.person.lsj.stock.bean.dongfang.moneyflow;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class StockMoneyFlowData {

    private Date dataDate;

    private float mainMoneyFlow;

    public Date getDataDate() {
        return dataDate;
    }

    public void setDataDate(Date dataDate) {
        this.dataDate = dataDate;
    }

    public float getMainMoneyFlow() {
        return mainMoneyFlow;
    }

    public void setMainMoneyFlow(float mainMoneyFlow) {
        this.mainMoneyFlow = mainMoneyFlow;
    }
}
