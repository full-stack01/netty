package cn.sp.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.sp.netty.c1.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws Exception {
        // selector 创建selector
        Selector selector = Selector.open();
        ByteBuffer buffer = ByteBuffer.allocate(5);
     // 使用nio来理解阻塞模式 单线程
        // 1创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2绑定监听端口
        ssc.bind(new InetSocketAddress(8899));
        // 3.链接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
             // 4.accept  SocketChannel 用来与客户端通信
            log.debug("connecting...");
            SocketChannel sc = ssc.accept(); // 阻塞方法，线程停止运行
            log.debug("connectted...{}",sc);
            channels.add(sc);
            for (SocketChannel channel :channels){
                log.debug("before read....{}",channel);
                int read = channel.read(buffer);// 阻塞方法，线程停止运行
                // 切换读模式
                buffer.flip();
                debugRead(buffer);
                // 切换写模式
                buffer.clear();
                log.debug("after read {}",channel);
            }
        }

    }
}
