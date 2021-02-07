package nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 19:32
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile rw = new RandomAccessFile("src/main/1.txt", "rw");
        // 获取对应的文件通道
        FileChannel channel = rw.getChannel();
        /**
         * 参数1：FileChannle.MapMode.READ_WRITE使用读写模式
         * 参数2：0   可以直接修改的起始位置
         * 参数3: 5   映射到内存的大小，即将1.txt的多少个字节映射到内存
         * 可以直接修改的范围就是[0-5)，并不包括 5,5的意思是从0开始数5个
         * 实际类型是DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'U');
        mappedByteBuffer.put(3, (byte) 'U');
        rw.close();
    }

}
