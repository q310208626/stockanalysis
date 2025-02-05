package com.person.lsj.stock.bean.dongfang.financial.profit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 利润表对象
 */
@Data
public class ApiIncomeResponse {
    @JsonProperty("version")
    private String version; // 版本号

    @JsonProperty("result")
    private Result result; // 结果数据

    @JsonProperty("success")
    private Boolean success; // 请求是否成功

    @JsonProperty("message")
    private String message; // 返回消息

    @JsonProperty("code")
    private Integer code; // 返回代码
}
