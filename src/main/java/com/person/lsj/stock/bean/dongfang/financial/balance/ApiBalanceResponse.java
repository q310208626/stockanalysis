package com.person.lsj.stock.bean.dongfang.financial.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 资产负债表对象
 */
@Data
public class ApiBalanceResponse {
    @JsonProperty("version")
    private String version;

    @JsonProperty("result")
    private Result result;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private int code;

    // Getters and Setters
}
