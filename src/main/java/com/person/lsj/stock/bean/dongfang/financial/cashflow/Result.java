package com.person.lsj.stock.bean.dongfang.financial.cashflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Result {
    @JsonProperty("pages")
    private Integer pages; // 总页数

    @JsonProperty("data")
    private List<CashFlowDataItem> data; // 数据列表

    @JsonProperty("count")
    private Integer count; // 数据总数
}
