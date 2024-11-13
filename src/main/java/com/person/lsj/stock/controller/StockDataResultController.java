package com.person.lsj.stock.controller;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.CustomDateFormat;
import com.person.lsj.stock.service.StockDataCapturerService;
import com.person.lsj.stock.service.StockDataResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/result")
public class StockDataResultController {
    @Autowired
    private StockDataResultService stockDataResultService;

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @GetMapping("/{searchDate}")
    public List<StockDataResultSum> getYestedayData(@PathVariable String searchDate) {
        System.out.println(searchDate);
        LocalDate collectDate = LocalDate.parse(searchDate, DateTimeFormatter.ofPattern(CustomDateFormat.DATE_FORMAT_TIGHT));
        List<StockDataResultSum> stockDataResultSumList = stockDataResultService.queryStockDataResult(collectDate);
        stockDataResultSumList.sort(new Comparator<StockDataResultSum>() {
            @Override
            public int compare(StockDataResultSum o1, StockDataResultSum o2) {
                return o1.getTaskId().compareTo(o2.getTaskId());
            }
        });

        for (StockDataResultSum stockDataResultSum : stockDataResultSumList) {
            List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList().stream().filter(x -> x.getStockCode() != null).toList();
            List<String> stockCodeList = stockDataResultDetailsList.stream().map(x -> x.getStockCode()).collect(Collectors.toList());
            Map<String, String> stockCodesNames = stockDataCapturerService.getStockCodesNames(stockCodeList);

            for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
                String stockCode = stockDataResultDetails.getStockCode();
                String stockName = stockCodesNames.get(stockCode);
                stockDataResultDetails.setStockName(stockName);
            }

            stockDataResultSum.setStockDataResultDetailsList(stockDataResultDetailsList);
        }

        return stockDataResultSumList;
    }
}
