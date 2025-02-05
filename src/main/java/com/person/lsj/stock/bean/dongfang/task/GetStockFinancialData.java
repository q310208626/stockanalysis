package com.person.lsj.stock.bean.dongfang.task;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.person.lsj.stock.bean.dongfang.financial.balance.ApiBalanceResponse;
import com.person.lsj.stock.bean.dongfang.financial.balance.BalanceDataItem;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class GetStockFinancialData implements Callable<ApiBalanceResponse> {
    private static Logger LOGGER = Logger.getLogger(GetStockFinancialData.class);

    private static String URL = "https://datacenter.eastmoney.com/securities/api/data/get";

    private String stockCode; // 股票代码

    private ObjectMapper objectMapper = new ObjectMapper(); // JSON 解析器

    private CountDownLatch countDownLatch;

    public GetStockFinancialData(String stockCode, CountDownLatch countDownLatch) {
        this.stockCode = stockCode;
        this.countDownLatch = countDownLatch;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ApiBalanceResponse call() throws URISyntaxException {
        LOGGER.debug("Start get GetStockFinancialData: " + stockCode);

        // 构建请求 URL
        URIBuilder uriBuilder = new URIBuilder(URL);

        // 添加参数
        uriBuilder.addParameter("type", "RPT_F10_FINANCE_GBALANCE");
        uriBuilder.addParameter("sty", "F10_FINANCE_GBALANCE");
        uriBuilder.addParameter("filter", "(SECUCODE=\"" + stockCode + ".SH\")(REPORT_DATE in ('2024-09-30','2024-06-30','2024-03-31','2023-12-31'))");
        uriBuilder.addParameter("p", "1");
        uriBuilder.addParameter("ps", "4");
        uriBuilder.addParameter("sr", "-1");
        uriBuilder.addParameter("st", "REPORT_DATE");
        uriBuilder.addParameter("source", "HSF10");
        uriBuilder.addParameter("client", "PC");

        // 创建 GET 请求
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // 发送请求并获取响应
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 检查响应状态码
            if (response.getCode() == 200) {
                // 获取响应内容
                String jsonResponse = EntityUtils.toString(response.getEntity());

                // 将 JSON 转换为 Java 对象
                ApiBalanceResponse reportResponse = objectMapper.readValue(jsonResponse, ApiBalanceResponse.class);

                // 处理结果（例如打印或存储）
                System.out.println("Fetched data for stock: " + stockCode);
                return reportResponse;
            } else {
                System.err.println("Failed to fetch data. Status code: " + response.getCode());
            }

            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            countDownLatch.countDown();
            LOGGER.debug("End get GetStockFinancialData: " + stockCode);
        }
    }
}
