package com.hins.sp21websocket.ws.ps;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/24
 */
public class RedisChannel {

    public static final String PINTEA_CHANNEL = "pintea:channel:";

    public static final String WS_TOPIC = PINTEA_CHANNEL + "ws_topic";

    public static final String WS_USER_MSG = PINTEA_CHANNEL + "ws_user_msg";

}
