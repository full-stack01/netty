package cn.sp.netty.c7;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author Paynesun
 * @title: TestLengthFileldDecoder
 * @projectName JAVASenior
 * @description: LTC解码器,固定长度，短链接，行解码器
 * @date 2022/6/12 17:26
 */
public class TestLengthFileldDecoder {

    public static void main(String[] args) {
       new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(
                        1024,0,4,0,4
                ),
                new LoggingHandler(LogLevel.DEBUG)
        );
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "hello world");
        send(buffer, "Hi!");
    }


    private static void send(ByteBuf buf,String context){
        byte[] bytes = context.getBytes(StandardCharsets.UTF_8);// 实际内容
        int length = bytes.length;// 实际内容长度
        buf.writeInt(length);
        buf.writeBytes(bytes);
    }
}
