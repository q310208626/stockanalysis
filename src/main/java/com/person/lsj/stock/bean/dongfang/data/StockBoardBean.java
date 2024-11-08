package com.person.lsj.stock.bean.dongfang.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockBoardBean {
    @JsonProperty("f12")
    private String boardCode;

    @JsonProperty("f14")
    private String boardName;

    @JsonProperty("f2")
    private String closePrice;

    @JsonProperty("f3")
    private String changePercent;

    @JsonProperty("f5")
    private String volume;

    @JsonProperty("f6")
    private String amount;

    @JsonProperty("f17")
    private String openPrice;

    // 换手率
    @JsonProperty("f8")
    private String TurnoverRate;

    // 五分钟涨幅
    @JsonProperty("f11")
    private String FiveMinuteChangePercent;
}
