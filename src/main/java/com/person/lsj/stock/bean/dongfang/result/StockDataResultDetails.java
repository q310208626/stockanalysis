package com.person.lsj.stock.bean.dongfang.result;

import lombok.Data;

@Data
public class StockDataResultDetails {
    private int detailsId;
    private int resultId;
    private String stockCode;
    private String stockName;
    private int result;
    private float open;
    private float close;
    private float increase;
}
