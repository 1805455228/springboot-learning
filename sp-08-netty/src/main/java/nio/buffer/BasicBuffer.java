package nio.buffer;

import java.nio.IntBuffer;

/**
 * @author qixuan.chen
 * @createDate 2019-11-28 21:47
 */
public class BasicBuffer {

    public static void main(String[] args) {
        // 举例说明Buffer的使用
        // 创建一个Buffer,大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer中存放数据
//        intBuffer.put(10);
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        // 如何从Buffer读取数据
        // 将Buffer转换，读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }

}
