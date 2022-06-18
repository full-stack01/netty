package cn.itcast.server.handler;

import cn.itcast.message.ChatRequestMessage;
import cn.itcast.message.ChatResponseMessage;
import cn.itcast.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Paynesun
 * @title: ChatRequestMessageHandler
 * @projectName netty-demo
 * @description: TODO
 * @date 2022/6/14 22:25
 */
@ChannelHandler.Sharable
@Slf4j
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String to = msg.getTo();
        String content = msg.getContent();
        String from = msg.getFrom();
        Channel channel = SessionFactory.getSession().getChannel(to);
        log.debug("@@@@channel:{},to:{}",channel,to);
        if (channel != null){
            // 代表在线
            channel.writeAndFlush(new ChatResponseMessage(from, content));
        }else {
            // 不在线
            ctx.writeAndFlush(new ChatResponseMessage(false, "当前不在线"));
        }
    }
}
