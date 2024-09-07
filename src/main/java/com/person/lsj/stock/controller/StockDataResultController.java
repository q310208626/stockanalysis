package com.person.lsj.stock.controller;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.service.StockDataCapturerService;
import com.person.lsj.stock.service.StockDataResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

    @GetMapping("/lastday")
    public List<StockDataResultSum> getYestedayData() {
        LocalDate lastWorkDay = stockDataResultService.getLatestWorkDay();
        List<StockDataResultSum> stockDataResultSumList = stockDataResultService.queryStockDataResult(lastWorkDay);
        stockDataResultSumList.sort(new Comparator<StockDataResultSum>() {
            @Override
            public int compare(StockDataResultSum o1, StockDataResultSum o2) {
                return o1.getTaskId().compareTo(o2.getTaskId());
            }
        });

        for (StockDataResultSum stockDataResultSum : stockDataResultSumList) {
            List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList();
            List<String> stockCodeList = stockDataResultDetailsList.stream().map(x -> x.getStockCode()).collect(Collectors.toList());
            Map<String, String> stockCodesNames = stockDataCapturerService.getStockCodesNames(stockCodeList);

            for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
                String stockCode = stockDataResultDetails.getStockCode();
                String stockName = stockCodesNames.get(stockCode);
                stockDataResultDetails.setStockName(stockName);
            }
        }

        return stockDataResultSumList;
    }

    @GetMapping("/secondToLastday")
    public List<StockDataResultSum> getSecondToLastDayData() {
        LocalDate secondToLastDay = stockDataResultService.getSecondToLastDay();
        if (secondToLastDay == null) {
            return List.of();
        }

        List<StockDataResultSum> stockDataResultSumList = stockDataResultService.queryStockDataResult(secondToLastDay);
        stockDataResultSumList.sort(new Comparator<StockDataResultSum>() {
            @Override
            public int compare(StockDataResultSum o1, StockDataResultSum o2) {
                return o1.getTaskId().compareTo(o2.getTaskId());
            }
        });

        for (StockDataResultSum stockDataResultSum : stockDataResultSumList) {
            List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList();
            List<String> stockCodeList = stockDataResultDetailsList.stream().map(x -> x.getStockCode()).collect(Collectors.toList());
            Map<String, String> stockCodesNames = stockDataCapturerService.getStockCodesNames(stockCodeList);

            for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
                String stockCode = stockDataResultDetails.getStockCode();
                String stockName = stockCodesNames.get(stockCode);
                stockDataResultDetails.setStockName(stockName);
            }
        }

        return stockDataResultSumList;
    }
}
