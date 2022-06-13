package cn.sp.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static cn.sp.netty.c1.ByteBufferUtil.debugAll;

@Slf4j
public class MyServer {
    public static void main(String[] args) throws Exception {
        // selector 创建selector
        Selector selector = Selector.open();
        // 创建socket
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 设置非阻塞
        ssc.configureBlocking(false);
        //绑定端口
        ssc.bind(new InetSocketAddress(8899));
        // 注册
        SelectionKey sscKey = ssc.register(selector, 0, null);
        log.debug("resgister:{}",sscKey);
        // 关注链接事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        while (true){
            // 发生事件要不处理要不取消，不能
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                // 一定要移除
                iterator.remove();
                if (key.isAcceptable()) {
                    log.debug("key{}",key);
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    log.debug("sc{}",sc);
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(6);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 取出附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        if (read == -1){
                            key.cancel();
                        }else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity()*2);
                                buffer.flip();
                                // 复制
                                newBuffer.put(buffer);
                                // 关联到附件上面
                                key.attach(buffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 取消事件
                        key.cancel();
                    }
                }
            }

        }
    }


    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到完整消息
            if (source.get(i) == '\n'){
                int len = i + 1 - source.position();
                // 存入新的bytebuffer
                ByteBuffer target = ByteBuffer.allocate(len);
                for (int j = 0; j < len; j++) {
                    target.put( source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}
