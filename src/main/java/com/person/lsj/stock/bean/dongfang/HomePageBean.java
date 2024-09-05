package com.person.lsj.stock.bean.dongfang;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"rc","rt","svr","lt","full","dlmkts"})
public class HomePageBean {
    @JsonProperty("data")
    private StockDataBean data;

    public StockDataBean getData() {
        return data;
    }

    public void setData(StockDataBean data) {
        this.data = data;
    }
}
