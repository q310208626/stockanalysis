package com.person.lsj.stock.enumeration;

public enum RESULT {
    SUCC(0),
    FAIL(1),
    UNKNOW(2);

    RESULT(int result) {
        this.result = result;
    }

    public int result;
}
