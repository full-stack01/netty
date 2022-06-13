package cn.sp.netty.c1;

import java.nio.ByteBuffer;

import static cn.sp.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author Paynesun
 * @title: TestByteBufferRead
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 20:48
 */
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();

        // rewind从头开始读
//        buffer.get(new byte[4]);
//        debugAll(buffer);
//        buffer.rewind();
//        System.out.println((char) buffer.get());

        // mark & rest
        // mark 做一个标记，记录position位置，rest是将position重置到mark的位置
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        buffer.mark();// 索引为2的位置
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        buffer.reset();//是将position重置到mark的位置 索引为2的位置
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get(3));
        debugAll(buffer);
    }
}
