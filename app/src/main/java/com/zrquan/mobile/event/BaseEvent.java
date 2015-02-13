package com.zrquan.mobile.event;

import com.zrquan.mobile.support.enums.EventCode;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.enums.ServerCode;

public abstract class BaseEvent {
    // 解析顺序一般为　1. event className  如:AccountEvent
    //               2. eventType  如:AE_NET_REGISTER
    //               3. eventCode  如:FA_SERVER_ERROR
    //               4. serverCode 如:S_OK, FA_USER_NOT_EXIT

    // 事件的类型 有两种判断形式，一种是类名 如BaseEvent, AccountEvent, RegisterEvent
    // eventType 则用于分辨同个AccountEvent中的不同，比如RegisterComplete, RegisterVerifyCode
    // 用于同类型事件的复用细分，自定义的，与服务器无关
    private EventType eventType;
    //事件的操作码，App本地操作结果，自定义的，与服务器无关
    private EventCode eventCode;
    //服务器返回码，与服务器端每次请求返回的Code一致
    private ServerCode serverCode;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventCode getEventCode() {
        return eventCode;
    }

    public void setEventCode(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    public ServerCode getServerCode() {
        return serverCode;
    }

    public void setServerCode(ServerCode serverCode) {
        this.serverCode = serverCode;
    }

    public final String LOG_TAG = getClass().getName();

    @Override
    public String toString() {
        return "\nEventClass:" + LOG_TAG + "\nEventType" + getEnumPropertyName(eventType)
                + "\nEventCode" + getEnumPropertyName(eventCode)
                + "\nServerCode" + getEnumPropertyName(serverCode);
    }

    public static String getEnumPropertyName (Enum e) {
        return (e != null) ? e.name() : "";
    }
}
