package cn.sp.netty.c1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Paynesun
 * @title: TestByteBuffer
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 17:18
 */

@Slf4j
public class TestByteBuffer {
    @Test
    public void test01() throws Exception{
        // 使用io读取
        FileInputStream fis = new FileInputStream(new File("data.txt"));
        byte[] buffer = new byte[5];
        int len;
        while ((len = fis.read(buffer)) != -1){
            String s = new String(buffer, 0, len);
            System.out.print(s);
        }
    }

    //使用nio
    @Test
    public void test02() throws Exception{
        // 获得filechannel
        //1.输入输出流.2RandomAccessFile
        FileChannel channel = new FileInputStream("data.txt").getChannel();
        // 准备缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(5);
        while (true){
            // 从channel 读取数据，向buffer写入
            int len = channel.read(buffer);
            log.debug("读取到的字节数{}",len);
            if (len == -1){
                break;
            }
            // 打印buffer内容
            buffer.flip();//读模式
            while (buffer.hasRemaining()){
                byte b = buffer.get();
                System.out.print ((char)b );
                log.debug("实际字节{}",(char) b);
            }
            // 切换写模式
            buffer.clear();
        }
    }

}
