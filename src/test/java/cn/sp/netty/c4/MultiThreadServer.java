package cn.sp.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import static cn.sp.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author Paynesun
 * @title: MultiThreadServer
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/8 21:09
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8899));
        // 创建固定数量worker
        Worker worker = new Worker("worker-0");
        while (true){
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()){
                   ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                   SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}",sc.getRemoteAddress());
                    // 关联selector
                    log.debug("before register...{}",sc.getRemoteAddress());
                    worker.register(sc);// boss调用 初始化selector 启动worker-0
                    log.debug("after register...{}",sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker  implements Runnable {
        private  Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        // 初始化线程和selector
        public void register(SocketChannel sc) throws Exception {
            if (!start){
                thread = new Thread(this,name);
                selector = Selector.open();
                thread.start();
                start = true;
            }
            queue.add(()->{
                try {
                    sc.register(selector, SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup(); // 唤醒
        }

        @Override
        public void run() {
            while (true){
                try {
                    selector.select();
                } catch (Exception e) {
                    e.printStackTrace();
                }//worker-0 阻塞住, wakeup
                    Runnable task = queue.poll();
                    if (task != null){
                        task.run();
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()){
                    SelectionKey key = iter.next();
                    iter.remove();
                        try {
                            if (key.isReadable()) {
                                ByteBuffer buffer = ByteBuffer.allocate(16);
                                SocketChannel channel = (SocketChannel) key.channel();
                                log.debug("read...{}",channel.getRemoteAddress());
                              // 需要处理
                                int read = channel.read(buffer);
                                if (read == -1){
                                    key.cancel();
                                }else {
                                    buffer.flip();
                                    debugAll(buffer);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            key.cancel();
                        }
                    }
            }
        }
    }
}
