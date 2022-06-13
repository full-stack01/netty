package cn.sp.netty.c1;

import java.nio.ByteBuffer;

/**
 * @author Paynesun
 * @title: TestByteBufferAllocate
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 20:29
 */
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        /**
         * class java.nio.HeapByteBuffer -java 堆内存  读写效率低，受到GC的影响
         * class java.nio.DirectByteBuffer -java 直接内存
         */
    }
}
