package com.zrquan.mobile.support.enums;

public enum EventCode {
    //事件操作成功，如果NET相关的事件类型，则指服务器返回code为"S_OK"
    S_OK,
    //事件操作失败，需要查看BaseEvent.getServerCode确认错误类型
    FA_SERVER_ERROR,
    //指服务器超时
    FA_SERVER_TIMEOUT,
    //指从本地获取jwt token时，发现它不存在
    FA_ACCESS_TOKEN_NOT_EXIT
}
