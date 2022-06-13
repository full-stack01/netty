package cn.sp.netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author Paynesun
 * @title: TestClient
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/8 21:35
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8899));
        sc.write(Charset.defaultCharset().encode("123456789abcdef"));
        System.in.read();
    }
}
