package cn.sp.netty.c5;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Paynesun
 * @title: TestEventLoop
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/10 22:37
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(2);// io 普通任务 定时任务
//        DefaultEventLoop group = new DefaultEventLoop();// 普通任务 定时任务
//        System.out.println(group.next());
//        System.out.println(group.next());
//        System.out.println(group.next());
//        group.next().submit(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.debug("ok");
//        });
//        log.debug("main");

        group.next().scheduleAtFixedRate(()->{
            log.debug("ok");
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

}
