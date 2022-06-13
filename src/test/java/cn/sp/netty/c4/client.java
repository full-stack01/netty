package cn.sp.netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author Paynesun
 * @title: client
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/6 21:26
 */
public class client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 8899));
        sc.write(Charset.defaultCharset().encode("123456789abc\n"));
       System.in.read();
    }
}
