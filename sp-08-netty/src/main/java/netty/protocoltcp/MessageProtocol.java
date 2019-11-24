package netty.protocoltcp;

import lombok.Data;

/**
 * 数据包协议
 * @author qixuan.chen
 * @date 2019-11-24 16:36
 */
@Data
public class MessageProtocol {

    private int len; //长度是关键

    private byte[] content;
}
