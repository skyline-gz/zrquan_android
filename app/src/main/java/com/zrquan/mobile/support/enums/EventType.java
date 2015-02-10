package com.zrquan.mobile.support.enums;

public enum EventType {
    //AccountEvent
    //命名规则, 前两位 AE从AccountEvent的两个大写，NET表示需要网络请求
    AE_NET_SEND_VERIFY_CODE,    //获取验证码
    AE_NET_VERIFY_JWT,          //验证JWT Token
    AE_NET_REGISTER             //用户注册
}
