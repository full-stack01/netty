package cn.sp.netty.c1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Paynesun
 * @title: TestFilesWalkFilesTree
 * @projectName JAVASenior
 * @description: TODO
 * @date 2022/6/5 22:15
 */
public class TestFilesWalkFilesTree {
    public static void main(String[] args) throws Exception {
        Files.walkFileTree(Paths.get("D:\\snipast - 副本"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //System.out.println("===>进入"+dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                //System.out.println("<===退出"+dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    private static void extracted1() throws IOException {
        AtomicInteger jarCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_202"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jar")) {
                    jarCount.incrementAndGet();
                    System.out.println(file);
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("jarCount"+jarCount);
    }

    private static void extracted() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_202"), new SimpleFileVisitor<Path>(){
           @Override
           public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
               System.out.println("======>"+dir);
               dirCount.incrementAndGet();
               return super.preVisitDirectory(dir, attrs);
           }

           @Override
           public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               System.out.println(file);
               fileCount.incrementAndGet();
               return super.visitFile(file, attrs);
           }
       });
        System.out.println("dirCount"+dirCount);
        System.out.println("fileCount"+fileCount);
    }
}
