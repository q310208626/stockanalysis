package com.person.lsj.stock.bean.dongfang.data;

import lombok.Data;

@Data
public class StockCurDetailsData {
    private String stockCode;
    private String stockName;
    private float close;
    private float cur;
    private float high;
    private float low;
    private float open;
    private int volume;
    private int increasePercentage;
}
