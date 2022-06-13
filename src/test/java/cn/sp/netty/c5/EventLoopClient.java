package cn.sp.netty.c5;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author Paynesun
 * @title: EventLoopClient
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/10 21:57
 */
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1. 连接到服务器
                // 异步非阻塞，main 发起了调用，真正执行connect 是nio线程
                .connect(new InetSocketAddress("localhost", 8899));
        // 2.1 使用sync 方法同步处理结果
        //channelFuture .sync();
        //Channel channel = channelFuture.channel();
        //channel.writeAndFlush("hello world");
        // 2.2 使用addListenner(回调对象) 方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            // NIO线程连接建立害了之后,会调用operationComplete
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = channelFuture.channel();
                channel.writeAndFlush("hello world");
            }
        });
    }
}
