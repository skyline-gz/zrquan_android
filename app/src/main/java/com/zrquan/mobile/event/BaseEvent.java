package com.zrquan.mobile.event;

import com.zrquan.mobile.support.em.EventCode;
import com.zrquan.mobile.support.em.EventType;
import com.zrquan.mobile.support.em.ServerCode;

public abstract class BaseEvent {
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
}
