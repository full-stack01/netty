package cn.sp.netty.c1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author Paynesun
 * @title: TestFileChannelTransferTo
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 21:59
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try {
            FileChannel from = new FileInputStream("hello.txt").getChannel();
            FileChannel to = new FileOutputStream("to.txt").getChannel();
            long size = from.size();
            for (long left = size; left >0;){
                left  -= from.transferTo((size - left),left, to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
