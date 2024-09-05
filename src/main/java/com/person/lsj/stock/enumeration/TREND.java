package com.person.lsj.stock.enumeration;

public enum TREND {
    TEND_UP("0", "上升"),
    TEND_DOWN("1", "下降"),
    TEND_RANDOM("*", "上升或下降");

    public String judgeRule;
    public String tendString;

    TREND(String judgeRule, String tendString) {
        this.judgeRule = judgeRule;
        this.tendString = tendString;
    }

    public static String getTendString(String judgeRule) {
        for (TREND value : TREND.values()) {
            if (value.judgeRule.equals(judgeRule)) {
                return value.tendString;
            }
        }

        return "无匹配";
    }

}
