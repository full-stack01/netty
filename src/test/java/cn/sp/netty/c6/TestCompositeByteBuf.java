package cn.sp.netty.c6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Paynesun
 * @title: TestCompositeByteBuf
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/12 14:01
 */
@Slf4j
public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer(10);
        buf1.writeBytes(new byte[]{1,2,3,4,5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer(10);
        buf2.writeBytes(new byte[]{6,7,8,9,10});

        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true,buf1,buf2);
        TestSlice.log(buffer);
    }
}
