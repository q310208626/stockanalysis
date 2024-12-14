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

                // DMI
                setDMI(stockDataEntities, i);

                // RSI
                setRsi(stockDataEntities, i);

                // CCI
                setCci(stockDataEntities, i);

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

    private void setDMI(List<StockDataEntity> stockDataEntities, int i) {
        float HD = 0;
        float LD = 0;
        float DMI_TR_SUM, DMI_DMP_SUM, DMI_DMM_SUM, DMI_ADX_SUM, DMI_ADXR_SUM = 0;
        if (i == 0) {
            stockDataEntities.get(i).setDmiTr(Math.max(Math.max(stockDataEntities.get(i).getHigh() - stockDataEntities.get(i).getLow(), Math.abs(stockDataEntities.get(i).getHigh() - stockDataEntities.get(i).getClose())), Math.abs(stockDataEntities.get(i).getClose() - stockDataEntities.get(i).getLow())));
            HD = 0;
            LD = 0;
        } else {
            stockDataEntities.get(i).setDmiTr(Math.max(Math.max(stockDataEntities.get(i).getHigh() - stockDataEntities.get(i).getLow(), Math.abs(stockDataEntities.get(i).getHigh() - stockDataEntities.get(i - 1).getClose())), Math.abs(stockDataEntities.get(i - 1).getClose() - stockDataEntities.get(i).getLow())));
            HD = stockDataEntities.get(i).getHigh() - stockDataEntities.get(i - 1).getHigh();
            LD = stockDataEntities.get(i - 1).getLow() - stockDataEntities.get(i).getLow();
        }

        if ((HD > 0) && (HD > LD)) {
            stockDataEntities.get(i).setDmiDmp(HD);
        } else {
            stockDataEntities.get(i).setDmiDmp(0);
        }

        if ((LD > 0) && (LD > HD)) {
            stockDataEntities.get(i).setDmiDmm(LD);
        } else {
            stockDataEntities.get(i).setDmiDmm(0);
        }

        if (i >= 13) {
            if (i == 13) {
                DMI_TR_SUM = DMI_DMP_SUM = DMI_DMM_SUM = 0;
                for (var j = 0; j < 14; j++) {
                    DMI_TR_SUM += stockDataEntities.get(i - j).getDmiTr();
                    DMI_DMP_SUM += stockDataEntities.get(i - j).getDmiDmp();
                    DMI_DMM_SUM += stockDataEntities.get(i - j).getDmiDmm();
                }
                stockDataEntities.get(i).setDmiExpmemaTr(DMI_TR_SUM / 14);
                stockDataEntities.get(i).setDmiExpmemaDmp(DMI_DMP_SUM / 14);
                stockDataEntities.get(i).setDmiExpmemaDmm(DMI_DMM_SUM / 14);
            } else {
                stockDataEntities.get(i).setDmiExpmemaTr((stockDataEntities.get(i).getDmiTr() * 2 + 13 * stockDataEntities.get(i - 1).getDmiExpmemaTr()) / 15);
                stockDataEntities.get(i).setDmiExpmemaDmp((stockDataEntities.get(i).getDmiDmp() * 2 + 13 * stockDataEntities.get(i - 1).getDmiExpmemaDmp()) / 15);
                stockDataEntities.get(i).setDmiExpmemaDmm((stockDataEntities.get(i).getDmiDmm() * 2 + 13 * stockDataEntities.get(i - 1).getDmiExpmemaDmm()) / 15);
            }
            if (stockDataEntities.get(i).getDmiExpmemaTr() != 0) {
                stockDataEntities.get(i).setDmiPdi(stockDataEntities.get(i).getDmiExpmemaDmp() * 100 / stockDataEntities.get(i).getDmiExpmemaTr());
                stockDataEntities.get(i).setDmiMdi(stockDataEntities.get(i).getDmiExpmemaDmm() * 100 / stockDataEntities.get(i).getDmiExpmemaTr());
                if (stockDataEntities.get(i).getDmiPdi() + stockDataEntities.get(i).getDmiMdi() != 0) {
                    stockDataEntities.get(i).setDmiMpdi(Math.abs(stockDataEntities.get(i).getDmiMdi() - stockDataEntities.get(i).getDmiPdi()) / (stockDataEntities.get(i).getDmiMdi() + stockDataEntities.get(i).getDmiPdi()) * 100);
                }
            }
        }

        if (i >= 18) {
            if (i == 18) {
                DMI_ADX_SUM = 0;
                for (var j = 0; j < 6; j++) {
                    DMI_ADX_SUM += stockDataEntities.get(i - j).getDmiMpdi();
                }
                stockDataEntities.get(i).setDmiAdx(DMI_ADX_SUM / 6);
            } else {
                stockDataEntities.get(i).setDmiAdx((stockDataEntities.get(i).getDmiMpdi() * 2 + 5 * stockDataEntities.get(i - 1).getDmiAdx()) / 7);
            }
        }

        if (i >= 23) {
            if (i == 23) {
                DMI_ADXR_SUM = 0;
                for (var j = 0; j < 6; j++) {
                    DMI_ADXR_SUM += stockDataEntities.get(i - j).getDmiAdx();
                }
                stockDataEntities.get(i).setDmiAdxr(DMI_ADXR_SUM / 6);
            } else {
                stockDataEntities.get(i).setDmiAdxr((stockDataEntities.get(i).getDmiAdx() * 2 + 5 * stockDataEntities.get(i - 1).getDmiAdxr()) / 7);
            }
        }

    }

    private void setBoll(List<StockDataEntity> stockDataEntities, int i) {
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

    private void setASI(List<StockDataEntity> stockDataEntities, int i) {
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

    private void setRsi(List<StockDataEntity> stockDataEntities, int i) {
        if (i > 0) {
            float RSI_UP = Math.max(stockDataEntities.get(i).getClose() - stockDataEntities.get(i - 1).getClose(), 0);
            float RSI_DN = Math.abs(stockDataEntities.get(i).getClose() - stockDataEntities.get(i - 1).getClose());
            if (i == 1) {
                stockDataEntities.get(i).setRsiUpA(RSI_UP);
                stockDataEntities.get(i).setRsiDnA(RSI_DN);
                stockDataEntities.get(i).setRsiUpB(RSI_UP);
                stockDataEntities.get(i).setRsiDnB(RSI_DN);
                stockDataEntities.get(i).setRsiUpC(RSI_UP);
                stockDataEntities.get(i).setRsiDnC(RSI_DN);
            }
            else {
                stockDataEntities.get(i).setRsiUpA(RSI_UP + stockDataEntities.get(i - 1).getRsiUpA() * (6 - 1) / 6);
                stockDataEntities.get(i).setRsiDnA(RSI_DN + stockDataEntities.get(i - 1).getRsiDnA() * (6 - 1) / 6);
                stockDataEntities.get(i).setRsiUpB(RSI_UP + stockDataEntities.get(i - 1).getRsiUpB() * (12 - 1) / 12);
                stockDataEntities.get(i).setRsiDnB(RSI_DN + stockDataEntities.get(i - 1).getRsiDnB() * (12 - 1) / 12);
                stockDataEntities.get(i).setRsiUpC(RSI_UP + stockDataEntities.get(i - 1).getRsiUpC() * (24 - 1) / 24);
                stockDataEntities.get(i).setRsiDnC(RSI_DN + stockDataEntities.get(i - 1).getRsiDnC() * (24 - 1) / 24);
            }
        }

        if (i > 4) {
            stockDataEntities.get(i).setRsiA(stockDataEntities.get(i).getRsiDnA() == 0 ? 0 : stockDataEntities.get(i).getRsiUpA() / stockDataEntities.get(i).getRsiDnA() * 100);
            stockDataEntities.get(i).setRsiB(stockDataEntities.get(i).getRsiDnB() == 0 ? 0 : stockDataEntities.get(i).getRsiUpB() / stockDataEntities.get(i).getRsiDnB() * 100);
            stockDataEntities.get(i).setRsiC(stockDataEntities.get(i).getRsiDnC() == 0 ? 0 : stockDataEntities.get(i).getRsiUpC() / stockDataEntities.get(i).getRsiDnC() * 100);
        }
    }

    private void setCci(List<StockDataEntity> stockDataEntities, int i) {
        stockDataEntities.get(i).setCciTyp((stockDataEntities.get(i).getHigh() + stockDataEntities.get(i).getLow() + stockDataEntities.get(i).getClose()) / 3);
        if (i >= 13) {
            float sum = 0;
            for (var j = 0; j < 14; j++) {
                sum += stockDataEntities.get(i - j).getClose();
            }
            float CCI_MA = sum / 14;
            sum = 0;
            for (var j = 0; j < 14; j++) {
                sum += stockDataEntities.get(i - j).getCciTyp();
            }
            float CCI_TYP_MA = sum / 14;
            sum = 0;
            for (var j = 0; j < 14; j++) {
                sum += Math.abs(stockDataEntities.get(i - j).getCciTyp() - CCI_TYP_MA);
            }
            if (sum != 0) {
                stockDataEntities.get(i).setCci((stockDataEntities.get(i).getCciTyp() - CCI_TYP_MA) / (0.015f * (sum / 14)));
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
