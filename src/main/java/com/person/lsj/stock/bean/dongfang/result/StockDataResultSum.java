package com.person.lsj.stock.bean.dongfang.result;

import lombok.Data;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.*;

@Data
public class StockDataResultSum {

    private int resultId;

    private String taskId;

    // forecast result
    private List<StockDataResultDetails> stockDataResultDetailsList;

    private LocalDate collectDate;

    private float accuracyRate;

    private String desc = "No Description";

    public StockDataResultSum() {
        stockDataResultDetailsList = new ArrayList<>();
    }
}
