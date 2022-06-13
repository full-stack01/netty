package cn.itcast.protocol;

/**
 * @author Paynesun
 * @title: TestMessageCodec
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/12 21:15
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        //EmbeddedChannel channel = new EmbeddedChannel(
        //        new LoggingHandler(),
        //        new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
        //        new MessageCodec());
        //// encode
        //LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
        //channel.writeOutbound(message);
        //// decode
        //ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        //new MessageCodec().encode(null, message, buf);
        //
        //ByteBuf f1 = buf.slice(0, 100);
        //ByteBuf f2 = buf.slice(100, buf.readableBytes() - 100);
        //f1.retain(); // 计数器加1
        //// 入站
        //channel.writeInbound(f1);
        //channel.writeInbound(f2);
    }
}
