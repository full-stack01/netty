package cn.sp.netty.c5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Paynesun
 * @title: TestJdkFuture
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/11 18:21
 */
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1 创建线程池
        ExecutorService service = Executors.newFixedThreadPool(2);
        // 2提交任务
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 50;
            }
        });
        log.debug("等待结果");
        log.debug("结果是{}",future.get());
    }
}
