package cn.sp.netty.c1;


import org.junit.Test;

import java.nio.ByteBuffer;

import static cn.sp.netty.c1.ByteBufferUtil.debugAll;

public class TestByteBufferReadWrite {

    @Test
    public void test01(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        debugAll(buffer) ;
        buffer.put(new byte[]{0x62,0x63,0x64});
        debugAll(buffer) ;
        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer) ;
        buffer.compact();
        debugAll(buffer);
        buffer.put(new byte[]{0x65,0x6f});
        debugAll(buffer);
    }
}
