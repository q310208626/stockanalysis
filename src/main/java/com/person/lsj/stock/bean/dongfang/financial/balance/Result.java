package com.person.lsj.stock.bean.dongfang.financial.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Result {
    @JsonProperty("pages")
    private int pages;

    @JsonProperty("data")
    private List<BalanceDataItem> data;

    @JsonProperty("count")
    private int count;

    // Getters and Setters
}