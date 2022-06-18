package cn.itcast.server.handler;

import cn.itcast.message.GroupChatRequestMessage;
import cn.itcast.message.GroupChatResponseMessage;
import cn.itcast.message.GroupCreateRequestMessage;
import cn.itcast.server.session.GroupSession;
import cn.itcast.server.session.GroupSessionFactory;
import cn.itcast.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Paynesun
 * @title: GroupChatRequestMessageHandler
 * @projectName netty-demo
 * @description: TODO
 * @date 2022/6/17 21:38
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage  msg) throws Exception {
        String groupName = msg.getGroupName();
        List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        for (Channel channel : membersChannel){
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(),msg.getContent() ));
        }
    }
}
