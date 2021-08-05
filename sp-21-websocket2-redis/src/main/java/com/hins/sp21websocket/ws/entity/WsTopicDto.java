package com.hins.sp21websocket.ws.entity;

import lombok.Data;

/**
 * Description: WebSocket topic 广播内容
 *
 * @author mpg
 * @date 2021/06/25
 */
@Data
public class WsTopicDto {

    private String topic;

    private String msg;
}
