package cn.sp.netty.c1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author Paynesun
 * @title: TestGatheringWrites
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 21:24
 */
public class TestGatheringWrites {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("孙鹏");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("孙鹏");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");
        try {
            FileChannel channel = new RandomAccessFile("words2.txt", "rw").getChannel();
            channel.write(new ByteBuffer[]{b1,b2,b3});

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
