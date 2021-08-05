package com.hins.sp21websocket.ws.entity;

import lombok.Data;

import java.util.List;

/**
 * Description: WebSocket topic 广播内容
 *
 * @author mpg
 * @date 2021/06/25
 */
@Data
public class WsUserMsgDto {

    private String msgPath;

    private List<Long> memberIds;

    private String msg;
}