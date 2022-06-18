package cn.itcast.server.handler;

import cn.itcast.message.GroupCreateRequestMessage;
import cn.itcast.message.GroupCreateResponseMessage;
import cn.itcast.server.session.Group;
import cn.itcast.server.session.GroupSession;
import cn.itcast.server.session.GroupSessionFactory;
import cn.itcast.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Paynesun
 * @title: GroupCreateRequestMessageHandler
 * @projectName netty-demo
 * @description: TODO
 * @date 2022/6/15 22:16
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if (group == null){
            Iterator<String> iterator = members.iterator();
            while (iterator.hasNext()){
                String name = iterator.next();
                Channel channel = SessionFactory.getSession().getChannel(name);
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "你已经加入"+groupName));
            }
            // 发送创建成功
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName+"创建成功"));
        }else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, groupName+"已经存在"));
        }
    }
}
