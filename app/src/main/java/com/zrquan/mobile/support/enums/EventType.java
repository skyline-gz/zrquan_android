package com.zrquan.mobile.support.enums;

public enum EventType {
    //AccountEvent
    //命名规则,  AAA_BBB_CCC
    // AAA  源事件名称的缩写，比如AE从AccountEvent的两个大写
    // BBB  表示事件来源，NET表示网络请求，DISK表示硬盘
    // CCC  描述事件操作，一般是动宾结构的组成
    AE_NET_SEND_VERIFY_CODE,    //获取验证码
    AE_NET_VERIFY_JWT,          //验证JWT Token
    AE_NET_REGISTER,            //用户注册
    AE_NET_LOGIN,               //用户登陆
    AE_NET_RESET_PASSWORD       //用户重设密码
}
