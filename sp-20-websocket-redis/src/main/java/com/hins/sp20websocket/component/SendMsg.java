package com.hins.sp20websocket.component;

/**
 * 按用户推送 （单发）
 * @author : chenqixuan
 * @date : 2021/4/29
 */
public class SendMsg extends SendMsgAll{
    /**
     * 用户ID
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
