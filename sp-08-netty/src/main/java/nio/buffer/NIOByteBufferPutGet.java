package nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 18:17
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(100);
        byteBuffer.putLong(9L);
        byteBuffer.putChar('啊');
        byteBuffer.putShort(((short) 4));
        // 取出
        byteBuffer.flip();
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        // short用long来取
        System.out.println(byteBuffer.getLong());
    }
}
