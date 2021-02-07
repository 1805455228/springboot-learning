package nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 18:27
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }
        buffer.flip();
        // 得到一个只读的Buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        // 读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println("readOnlyBuffer = " + readOnlyBuffer.get());
        }
        // ReadOnlyBufferException
        readOnlyBuffer.put((byte)100);
    }
}
