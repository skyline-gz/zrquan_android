package com.zrquan.mobile.event;

public class VerifyAccountEvent {

    public int code;

    public static int S_OK = 0;
    public static int FA_ACCESS_TOKEN_NOT_EXIT = 1;

    public VerifyAccountEvent(int code) {
        this.code = code;
    }
}
