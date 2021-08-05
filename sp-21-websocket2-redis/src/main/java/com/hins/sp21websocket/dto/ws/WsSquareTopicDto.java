package com.hins.sp21websocket.dto.ws;


import com.hins.sp21websocket.api.CommonResult;
import com.hins.sp21websocket.dto.RedisRigUpOrderResp;
import lombok.Data;

import java.util.List;

/**
 * Description: WebSocket topic 广播内容
 *
 * @author mpg
 * @date 2021/06/25
 */
@Data
public class WsSquareTopicDto {

    private String topic;

    private CommonResult<List<RedisRigUpOrderResp>> msg;
}
