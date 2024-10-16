package com.person.lsj.stock.bean.dongfang.data;

import lombok.Data;

@Data
public class StockCurDetailsData {
    private String stockCode;
    private String stockName;
    private String close;
    private String cur;
    private String high;
    private String low;
    private String open;
    private int volume;
    private String increasePercentage;
}
