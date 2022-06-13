package cn.sp.netty.c1;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static cn.sp.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author Paynesun
 * @title: TestScatteringReads
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 21:10
 */
public class TestScatteringReads {
    public static void main(String[] args) {
        try {
            RandomAccessFile rw = new RandomAccessFile("words.txt", "r");
            FileChannel channel = rw.getChannel();
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1,b2,b3});
            b1.flip();
            b2.flip();
            b3.flip();
            debugAll(b1);
            debugAll(b2);
            debugAll(b3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
