package com.person.lsj.stock.bean.dongfang.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class StockDetailsData {
    private String stockCode;
    private String stockName;
    private List<StockDataEntity> stockDataEntities;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public List<StockDataEntity> getStockDataEntities() {
        return stockDataEntities;
    }

    public void setStockDataEntities(List<StockDataEntity> stockDataEntities) {
        try {
            this.stockDataEntities = stockDataEntities;
            for (int i = 0; i < stockDataEntities.size(); i++) {
                // Average
                setAverageByDayNum(stockDataEntities, 5, i);
                setAverageByDayNum(stockDataEntities, 10, i);
                setAverageByDayNum(stockDataEntities, 20, i);
                setAverageByDayNum(stockDataEntities, 30, i);
                setAverageByDayNum(stockDataEntities, 3, i);
                setAverageByDayNum(stockDataEntities, 6, i);
                setAverageByDayNum(stockDataEntities, 12, i);
                setAverageByDayNum(stockDataEntities, 24, i);
                setAverageByDayNum(stockDataEntities, 50, i);
                setAverageByDayNum(stockDataEntities, 60, i);

                // Volume
                setVolumeByDayNum(stockDataEntities, 5, i);
                setVolumeByDayNum(stockDataEntities, 10, i);
                setVolumeByDayNum(stockDataEntities, 30, i);
                setVolumeByDayNum(stockDataEntities, 60, i);

                // EXPMA
                float pre_expma12 = i == 0 ? stockDataEntities.get(i).getClose() : stockDataEntities.get(i - 1).getExpma12();
                stockDataEntities.get(i).setExpma12((stockDataEntities.get(i).getClose() * 2 + pre_expma12 * (12 - 1)) / (12 + 1));

                float pre_expma50 = i == 0 ? stockDataEntities.get(i).getClose() : stockDataEntities.get(i - 1).getExpma50();
                stockDataEntities.get(i).setExpma50((stockDataEntities.get(i).getClose() * 2 + pre_expma50 * (50 - 1)) / (50 + 1));

                // ASI
                setASI(stockDataEntities, i);

                // BOLL
                setBoll(stockDataEntities, i);

                // MACD
                if (i == 0) {
                    stockDataEntities.get(i).setMacdAx(stockDataEntities.get(i).getClose());
                    stockDataEntities.get(i).setMacdBx(stockDataEntities.get(i).getClose());
                    stockDataEntities.get(i).setMacdDif(0);
                    stockDataEntities.get(i).setMacdDea(0);
                } else {
                    stockDataEntities.get(i).setMacdAx((2 * stockDataEntities.get(i).getClose() + 11 * stockDataEntities.get(i - 1).getMacdAx()) / 13);
                    stockDataEntities.get(i).setMacdBx((2 * stockDataEntities.get(i).getClose() + 25 * stockDataEntities.get(i - 1).getMacdBx()) / 27);
                    stockDataEntities.get(i).setMacdDif(stockDataEntities.get(i).getMacdAx() - stockDataEntities.get(i).getMacdBx());
                    stockDataEntities.get(i).setMacdDea((2 * stockDataEntities.get(i).getMacdDif() + 8 * stockDataEntities.get(i - 1).getMacdDea()) / 10);
                }
                stockDataEntities.get(i).setMacd(2 * (stockDataEntities.get(i).getMacdDif() - stockDataEntities.get(i).getMacdDea()));

                // KDJ
                float LLV = stockDataEntities.get(i).getLow();
                float HHV = stockDataEntities.get(i).getHigh();
                for (int j = 0; j < 9 && j < i + 1; j++) {
                    if (HHV < stockDataEntities.get(i - j).getHigh()) {
                        HHV = stockDataEntities.get(i - j).getHigh();
                    }
                    if (LLV > stockDataEntities.get(i - j).getLow()) {
                        LLV = stockDataEntities.get(i - j).getLow();
                    }
                }
                if (HHV != LLV) {
                    stockDataEntities.get(i).setKdjRsv((stockDataEntities.get(i).getClose() - LLV) / (HHV - LLV) * 100);
                }
                if (i == 0) {
                    stockDataEntities.get(i).setKdjK(stockDataEntities.get(i).getKdjRsv());
                    stockDataEntities.get(i).setKdjD(stockDataEntities.get(i).getKdjRsv());
                    stockDataEntities.get(i).setKdjJ(stockDataEntities.get(i).getKdjRsv());
                } else {
                    stockDataEntities.get(i).setKdjK((stockDataEntities.get(i).getKdjRsv() / 3) + ((stockDataEntities.get(i - 1).getKdjK() * 2) / 3));
                    stockDataEntities.get(i).setKdjD((stockDataEntities.get(i).getKdjK() / 3) + ((stockDataEntities.get(i - 1).getKdjD() * 2) / 3));
                    stockDataEntities.get(i).setKdjJ((stockDataEntities.get(i).getKdjK() * 3) - (stockDataEntities.get(i).getKdjD() * 2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setBoll(List<StockDataEntity> stockDataEntities, int i) {
        if (i >= 19) {
            float sum = 0;
            for (var j = 0; j < 20; j++) {
                sum += stockDataEntities.get(i -j).getClose();
            }
            stockDataEntities.get(i).setBoll(sum / 20);

            sum = 0;
            for (var j = 0; j < 20; j++) {
                sum += (stockDataEntities.get(i - j).getClose() - stockDataEntities.get(i).getBoll()) * (stockDataEntities.get(i - j).getClose() - stockDataEntities.get(i).getBoll());
            }
            float bollMid = (float) Math.sqrt(sum / 20);
            stockDataEntities.get(i).setBollUpper(stockDataEntities.get(i).getBoll() + bollMid * 2);
            stockDataEntities.get(i).setBollLower(stockDataEntities.get(i).getBoll() - bollMid * 2);
        }
    }

    private static void setASI(List<StockDataEntity> stockDataEntities, int i) {
        if (i >= 1) {
            float LC = stockDataEntities.get(i - 1).getClose();
            float AA = Math.abs(stockDataEntities.get(i).getHigh() - LC);
            float BB = Math.abs(stockDataEntities.get(i).getLow() - LC);
            float CC = Math.abs(stockDataEntities.get(i).getHigh() - stockDataEntities.get(i - 1).getLow());
            float DD = Math.abs(LC - stockDataEntities.get(i - 1).getOpen());
            float R;
            if ((AA > BB) && (AA > CC)) {
                R = AA + BB / 2 + DD / 4;
            } else if ((BB > CC) && (BB > AA)) {
                R = BB + AA / 2 + DD / 4;
            } else {
                R = CC + DD / 4;
            }
            float X = stockDataEntities.get(i).getClose() + (stockDataEntities.get(i).getClose() - stockDataEntities.get(i).getOpen()) / 2 - stockDataEntities.get(i - 1).getOpen();
            if (R != 0) {
                stockDataEntities.get(i).setASI(stockDataEntities.get(i - 1).getASI() + 16 * X / R * Math.max(AA, BB));
            }
        }
    }

    private static void setAverageByDayNum(List<StockDataEntity> stockDataEntities, int dayNum, int i) {
        if (i >= dayNum -1) {
            float sum = 0;
            for (var j = 0; j < dayNum; j++) {
                sum += stockDataEntities.get(i - j).getClose();
            }
            try {
                Method setAverageMethodDayNum = StockDataEntity.class.getDeclaredMethod("setAverage" + dayNum, float.class);
                StockDataEntity curStockDataEntity = stockDataEntities.get(i);
                setAverageMethodDayNum.invoke(curStockDataEntity, sum / dayNum);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setVolumeByDayNum(List<StockDataEntity> stockDataEntities, int dayNum, int i) {
        if (i >= dayNum -1) {
            float vsum = 0;
            for (var j = 0; j < dayNum; j++) {
                vsum += stockDataEntities.get(i - j).getVolume();
            }
            try {
                Method setVolumeMethodDayNum = StockDataEntity.class.getDeclaredMethod("setVolume" + dayNum, float.class);
                StockDataEntity curStockDataEntity = stockDataEntities.get(i);
                setVolumeMethodDayNum.invoke(curStockDataEntity, vsum / dayNum);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
