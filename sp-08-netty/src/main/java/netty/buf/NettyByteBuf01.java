package netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author qixuan.chen
 * @date 2020-03-09 21:17
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        // 创建一个ByteBuf
        /**
         * 1. 创建一个对象，该对象包含一个数组arr，是一个byte[10]
         * 2. 在netty的Buffer中，不需要使用flip进行反转就可以读取
         *      因为底层维护了readerIndex以及writerIndex
         * 3. 通过readerIndex writerIndex capactiy将buffer分为三部分
         *                0====readerIndex    已经读取的区域
         *      readerIndex====writerIndex    可读区域
         *      writerIndex====capacity       可写区域
         */
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        // 总容量
        System.out.println("buffer.capacity() = " + buffer.capacity());
        // 输出
        for (int i = 0; i < buffer.capacity(); i++) {
            // 这里用的是随机读取，因此readIndex不会变化
            System.out.println("buffer.getByte(i) = " + buffer.getByte(i));
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            // 这里readByte才会导致readIndex增加
            System.out.println("buffer.readByte() = " + buffer.readByte());
        }
    }

}

