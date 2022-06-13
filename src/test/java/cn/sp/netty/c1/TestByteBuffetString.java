package cn.sp.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.sp.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author Paynesun
 * @title: TestByteBuffetString
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 20:58
 */
public class TestByteBuffetString {
    public static void main(String[] args) {
        // 1字符串转化为ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        debugAll(buffer);

        // charset
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer1);
        // 3.wrap
        ByteBuffer buffer2 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer2);
        String str1 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str1);
    }
}
