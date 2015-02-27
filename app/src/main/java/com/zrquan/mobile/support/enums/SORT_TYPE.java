package com.zrquan.mobile.support.enums;

/**
 * Created by James_Ouyang on 2015/2/27.
 */
public enum SORT_TYPE {
    DEFAULT("hot"), HOT("hot"), NEW("new");

    private String value;

    private SORT_TYPE(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
