package com.person.lsj.stock.bean.dongfang.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StockDataEntity {
    private float ASI;
    private float average3;
    private float average5;
    private float average6;
    private float average10;
    private float average12;
    private float average20;
    private float average24;
    private float average30;
    private float average50;
    private float average60;

    private float bbi;
    private float biasA;
    private float biasB;
    private float biasC;

    private float boll;
    private float bollLower;
    private float bollUpper;

    private float cci;
    private float cciTvp;

    private float cr;
    private float crA;
    private float crAX;
    private float crB;
    private float crBX;
    private float crC;
    private float crMID;

    private float dmiAdx;
    private float dmiAdxr;
    private float dmiDmm;
    private float dmiDmp;
    private float dmiExpmemaDmm;
    private float dmiExpmemaDmp;
    private float dmiExpmemaTr;
    private float dmiMdi;
    private float dmiMpdi;
    private float dmiPdi;
    private float dmiTr;

    private float kdjD;
    private float kdjJ;
    private float kdjK;
    private float kdjRsv;

    private float macd;
    private float macdAx;
    private float macdBx;
    private float macdDea;
    private float macdDif;

    private float obv;
    private float obvMa;
    private float roc;
    private float rocMa;

    private float rsiA;
    private float rsiB;
    private float rsiC;
    private float rsiDnA;
    private float rsiDnB;
    private float rsiDnC;
    private float rsiUpA;
    private float rsiUpB;
    private float rsiUpC;

    private float sar;
    private float sarRed;
    private float vr;
    private float vrMa;
    private float wrA;
    private float wrB;

    private float zero;
    private float close;
    private float high;
    private float low;
    private float open;
    private LocalDate time;
    private float increasePercentage;
    private float volume;
    private float volume5;
    private float volume10;
    private float volume30;
    private float volume60;

    private float expma12;
    private float expma50;

    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public StockDataEntity(String data) {
        String[] datas = data.split(",");
        String timeStr = datas[0];
        this.time = LocalDate.parse(timeStr, DateTimeFormatter.ISO_LOCAL_DATE);
        this.open = Float.valueOf(datas[1]);
        this.close = Float.valueOf(datas[2]);
        this.high = Float.valueOf(datas[3]);
        this.low = Float.valueOf(datas[4]);
        this.volume = Float.valueOf(datas[5]);
        this.increasePercentage = Float.valueOf(datas[8]);
    }

    public float getASI() {
        return ASI;
    }

    public void setASI(float ASI) {
        this.ASI = ASI;
    }

    public float getAverage3() {
        return average3;
    }

    public void setAverage3(float average3) {
        this.average3 = average3;
    }

    public float getAverage5() {
        return average5;
    }

    public void setAverage5(float average5) {
        this.average5 = average5;
    }

    public float getAverage6() {
        return average6;
    }

    public void setAverage6(float average6) {
        this.average6 = average6;
    }

    public float getAverage10() {
        return average10;
    }

    public void setAverage10(float average10) {
        this.average10 = average10;
    }

    public float getAverage12() {
        return average12;
    }

    public void setAverage12(float average12) {
        this.average12 = average12;
    }

    public float getAverage20() {
        return average20;
    }

    public void setAverage20(float average20) {
        this.average20 = average20;
    }

    public float getAverage24() {
        return average24;
    }

    public void setAverage24(float average24) {
        this.average24 = average24;
    }

    public float getAverage30() {
        return average30;
    }

    public void setAverage30(float average30) {
        this.average30 = average30;
    }

    public float getAverage50() {
        return average50;
    }

    public void setAverage50(float average50) {
        this.average50 = average50;
    }

    public float getAverage60() {
        return average60;
    }

    public void setAverage60(float average60) {
        this.average60 = average60;
    }

    public float getBbi() {
        return bbi;
    }

    public void setBbi(float bbi) {
        this.bbi = bbi;
    }

    public float getBiasA() {
        return biasA;
    }

    public void setBiasA(float biasA) {
        this.biasA = biasA;
    }

    public float getBiasB() {
        return biasB;
    }

    public void setBiasB(float biasB) {
        this.biasB = biasB;
    }

    public float getBiasC() {
        return biasC;
    }

    public void setBiasC(float biasC) {
        this.biasC = biasC;
    }

    public float getBoll() {
        return boll;
    }

    public void setBoll(float boll) {
        this.boll = boll;
    }

    public float getBollLower() {
        return bollLower;
    }

    public void setBollLower(float bollLower) {
        this.bollLower = bollLower;
    }

    public float getBollUpper() {
        return bollUpper;
    }

    public void setBollUpper(float bollUpper) {
        this.bollUpper = bollUpper;
    }

    public float getCci() {
        return cci;
    }

    public void setCci(float cci) {
        this.cci = cci;
    }

    public float getCciTvp() {
        return cciTvp;
    }

    public void setCciTvp(float cciTvp) {
        this.cciTvp = cciTvp;
    }

    public float getCr() {
        return cr;
    }

    public void setCr(float cr) {
        this.cr = cr;
    }

    public float getCrA() {
        return crA;
    }

    public void setCrA(float crA) {
        this.crA = crA;
    }

    public float getCrAX() {
        return crAX;
    }

    public void setCrAX(float crAX) {
        this.crAX = crAX;
    }

    public float getCrB() {
        return crB;
    }

    public void setCrB(float crB) {
        this.crB = crB;
    }

    public float getCrBX() {
        return crBX;
    }

    public void setCrBX(float crBX) {
        this.crBX = crBX;
    }

    public float getCrC() {
        return crC;
    }

    public void setCrC(float crC) {
        this.crC = crC;
    }

    public float getCrMID() {
        return crMID;
    }

    public void setCrMID(float crMID) {
        this.crMID = crMID;
    }

    public float getDmiAdx() {
        return dmiAdx;
    }

    public void setDmiAdx(float dmiAdx) {
        this.dmiAdx = dmiAdx;
    }

    public float getDmiAdxr() {
        return dmiAdxr;
    }

    public void setDmiAdxr(float dmiAdxr) {
        this.dmiAdxr = dmiAdxr;
    }

    public float getDmiDmm() {
        return dmiDmm;
    }

    public void setDmiDmm(float dmiDmm) {
        this.dmiDmm = dmiDmm;
    }

    public float getDmiDmp() {
        return dmiDmp;
    }

    public void setDmiDmp(float dmiDmp) {
        this.dmiDmp = dmiDmp;
    }

    public float getDmiExpmemaDmm() {
        return dmiExpmemaDmm;
    }

    public void setDmiExpmemaDmm(float dmiExpmemaDmm) {
        this.dmiExpmemaDmm = dmiExpmemaDmm;
    }

    public float getDmiExpmemaDmp() {
        return dmiExpmemaDmp;
    }

    public void setDmiExpmemaDmp(float dmiExpmemaDmp) {
        this.dmiExpmemaDmp = dmiExpmemaDmp;
    }

    public float getDmiExpmemaTr() {
        return dmiExpmemaTr;
    }

    public void setDmiExpmemaTr(float dmiExpmemaTr) {
        this.dmiExpmemaTr = dmiExpmemaTr;
    }

    public float getDmiMdi() {
        return dmiMdi;
    }

    public void setDmiMdi(float dmiMdi) {
        this.dmiMdi = dmiMdi;
    }

    public float getDmiMpdi() {
        return dmiMpdi;
    }

    public void setDmiMpdi(float dmiMpdi) {
        this.dmiMpdi = dmiMpdi;
    }

    public float getDmiPdi() {
        return dmiPdi;
    }

    public void setDmiPdi(float dmiPdi) {
        this.dmiPdi = dmiPdi;
    }

    public float getDmiTr() {
        return dmiTr;
    }

    public void setDmiTr(float dmiTr) {
        this.dmiTr = dmiTr;
    }

    public float getKdjD() {
        return kdjD;
    }

    public void setKdjD(float kdjD) {
        this.kdjD = kdjD;
    }

    public float getKdjJ() {
        return kdjJ;
    }

    public void setKdjJ(float kdjJ) {
        this.kdjJ = kdjJ;
    }

    public float getKdjK() {
        return kdjK;
    }

    public void setKdjK(float kdjK) {
        this.kdjK = kdjK;
    }

    public float getKdjRsv() {
        return kdjRsv;
    }

    public void setKdjRsv(float kdjRsv) {
        this.kdjRsv = kdjRsv;
    }

    public float getMacd() {
        return macd;
    }

    public void setMacd(float macd) {
        this.macd = macd;
    }

    public float getMacdAx() {
        return macdAx;
    }

    public void setMacdAx(float macdAx) {
        this.macdAx = macdAx;
    }

    public float getMacdBx() {
        return macdBx;
    }

    public void setMacdBx(float macdBx) {
        this.macdBx = macdBx;
    }

    public float getMacdDea() {
        return macdDea;
    }

    public void setMacdDea(float macdDea) {
        this.macdDea = macdDea;
    }

    public float getMacdDif() {
        return macdDif;
    }

    public void setMacdDif(float macdDif) {
        this.macdDif = macdDif;
    }

    public float getObv() {
        return obv;
    }

    public void setObv(float obv) {
        this.obv = obv;
    }

    public float getObvMa() {
        return obvMa;
    }

    public void setObvMa(float obvMa) {
        this.obvMa = obvMa;
    }

    public float getRoc() {
        return roc;
    }

    public void setRoc(float roc) {
        this.roc = roc;
    }

    public float getRocMa() {
        return rocMa;
    }

    public void setRocMa(float rocMa) {
        this.rocMa = rocMa;
    }

    public float getRsiA() {
        return rsiA;
    }

    public void setRsiA(float rsiA) {
        this.rsiA = rsiA;
    }

    public float getRsiB() {
        return rsiB;
    }

    public void setRsiB(float rsiB) {
        this.rsiB = rsiB;
    }

    public float getRsiC() {
        return rsiC;
    }

    public void setRsiC(float rsiC) {
        this.rsiC = rsiC;
    }

    public float getRsiDnA() {
        return rsiDnA;
    }

    public void setRsiDnA(float rsiDnA) {
        this.rsiDnA = rsiDnA;
    }

    public float getRsiDnB() {
        return rsiDnB;
    }

    public void setRsiDnB(float rsiDnB) {
        this.rsiDnB = rsiDnB;
    }

    public float getRsiDnC() {
        return rsiDnC;
    }

    public void setRsiDnC(float rsiDnC) {
        this.rsiDnC = rsiDnC;
    }

    public float getRsiUpA() {
        return rsiUpA;
    }

    public void setRsiUpA(float rsiUpA) {
        this.rsiUpA = rsiUpA;
    }

    public float getRsiUpB() {
        return rsiUpB;
    }

    public void setRsiUpB(float rsiUpB) {
        this.rsiUpB = rsiUpB;
    }

    public float getRsiUpC() {
        return rsiUpC;
    }

    public void setRsiUpC(float rsiUpC) {
        this.rsiUpC = rsiUpC;
    }

    public float getSar() {
        return sar;
    }

    public void setSar(float sar) {
        this.sar = sar;
    }

    public float getSarRed() {
        return sarRed;
    }

    public void setSarRed(float sarRed) {
        this.sarRed = sarRed;
    }

    public float getVr() {
        return vr;
    }

    public void setVr(float vr) {
        this.vr = vr;
    }

    public float getVrMa() {
        return vrMa;
    }

    public void setVrMa(float vrMa) {
        this.vrMa = vrMa;
    }

    public float getWrA() {
        return wrA;
    }

    public void setWrA(float wrA) {
        this.wrA = wrA;
    }

    public float getWrB() {
        return wrB;
    }

    public void setWrB(float wrB) {
        this.wrB = wrB;
    }

    public float getZero() {
        return zero;
    }

    public void setZero(float zero) {
        this.zero = zero;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume5() {
        return volume5;
    }

    public void setVolume5(float volume5) {
        this.volume5 = volume5;
    }

    public float getVolume10() {
        return volume10;
    }

    public void setVolume10(float volume10) {
        this.volume10 = volume10;
    }

    public float getVolume30() {
        return volume30;
    }

    public void setVolume30(float volume30) {
        this.volume30 = volume30;
    }

    public float getVolume60() {
        return volume60;
    }

    public void setVolume60(float volume60) {
        this.volume60 = volume60;
    }

    public float getExpma12() {
        return expma12;
    }

    public void setExpma12(float expma12) {
        this.expma12 = expma12;
    }

    public float getExpma50() {
        return expma50;
    }

    public void setExpma50(float expma50) {
        this.expma50 = expma50;
    }

    public float getIncreasePercentage() {
        return increasePercentage;
    }

    public void setIncreasePercentage(float increasePercentage) {
        this.increasePercentage = increasePercentage;
    }

    public static ThreadLocal<SimpleDateFormat> getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(ThreadLocal<SimpleDateFormat> dateFormat) {
        StockDataEntity.dateFormat = dateFormat;
    }
}
