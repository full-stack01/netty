package cn.itcast.protocol;

import cn.itcast.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author Paynesun
 * @title: MessageCodec
 * @projectName JAVASenior
 * @description: 编解码器
 * @date 2022/6/12 20:31
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    /*
     * 魔数，用来在第一时间判定是否是无效数据包
     * 版本号，可以支持协议的升级
     * 序列化算法，消息正文到底采用哪种序列化反序列化方式，可以由此扩展，例如：json、protobuf、hessian、jdk
     * 指令类型，是登录、注册、单聊、群聊... 跟业务相关
     * 请求序号，为了双工通信，提供异步能力
     * 正文长度
     * 消息正文*/
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 1. 4字节的魔数
        out.writeBytes(new byte[]{'y','y','d','s'});
        // 2 1字节的版本
        out.writeByte(1);
        // 3. 1字节序列化算法 jdk 0，json 1
        out.writeByte(0);
        // 4. 1字节指令类型
        out.writeByte(msg.getMessageType());
        // 5  4个字节请求序号
        out.writeInt(msg.getSequenceId());
        // 无意义1字节
        out.writeByte(0xff);
        // 6 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        // 7长度
        out.writeInt(bytes.length);
        // 8 写入内容
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes,0,length);
        ByteArrayInputStream bas = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bas);
        Message message = (Message) ois.readObject();
        //log.debug("魔数:{},版本:{},序列化类型:{},指令类型:{},请求序号:{},长度:{}",magicNum,version,serializerType,messageType,sequenceId,length);
        //log.debug("对象:{}",message);
        out.add(message);
    }
}
