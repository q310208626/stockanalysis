package com.person.lsj.stock.bean.dongfang.moneyflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StockMoneyFlowData {

    private Date dataDate;

    private float mainMoneyFlow;

    private float largeOrderMoney;

    private float superLargeOrderMoney;
}
