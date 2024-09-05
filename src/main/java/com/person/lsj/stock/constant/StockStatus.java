package com.person.lsj.stock.constant;

public enum StockStatus {
    SUSPEND(6,"停牌"),
    DELISTED(7,"退市"),
    SUSPEND_LISTING(8,"暂停上市"),
    NOT_LISTING(9, "未上市");

    StockStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int status;
    public String msg;
}
