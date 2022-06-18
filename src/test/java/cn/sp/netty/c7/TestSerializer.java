package cn.sp.netty.c7;

import cn.itcast.config.Config;
import cn.itcast.message.LoginRequestMessage;
import cn.itcast.message.Message;
import cn.itcast.protocol.MessageCodecSharable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Paynesun
 * @title: TestSerializer
 * @projectName netty-demo
 * @description: TODO
 * @date 2022/6/18 17:46
 */
public class TestSerializer {
    public static void main(String[] args) {
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        LoggingHandler loggingHandler = new LoggingHandler();
        EmbeddedChannel channel = new EmbeddedChannel(loggingHandler,messageCodecSharable,loggingHandler);
        LoginRequestMessage message = new LoginRequestMessage("张三", "123");
        //channel.writeOutbound(message);
        ByteBuf buf = messageToByteBuf(message);
        channel.writeInbound(buf);
    }


    public static ByteBuf messageToByteBuf(Message msg){
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        // 1. 4 字节的魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 2. 1 字节的版本,
        out.writeByte(1);
        // 3. 1 字节的序列化方式 jdk 0 , json 1
        out.writeByte(Config.getSerializerAlgorithm().ordinal());
        // 4. 1 字节的指令类型
        out.writeByte(msg.getMessageType());
        // 5. 4 个字节
        out.writeInt(msg.getSequenceId());
        // 无意义，对齐填充
        out.writeByte(0xff);
        // 6. 获取内容的字节数组
        byte[] bytes = Config.getSerializerAlgorithm().serialize(msg);
        // 7. 长度
        out.writeInt(bytes.length);
        // 8. 写入内容
        out.writeBytes(bytes);
        return out;
    }
}
