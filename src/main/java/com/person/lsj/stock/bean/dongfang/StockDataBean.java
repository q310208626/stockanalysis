package com.person.lsj.stock.bean.dongfang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockDataBean {
    @JsonProperty("total")
    private int total;

    @JsonProperty("diff")
    private StockField[] diff;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public StockField[] getDiff() {
        return diff;
    }

    public void setDiff(StockField[] diff) {
        this.diff = diff;
    }
}
