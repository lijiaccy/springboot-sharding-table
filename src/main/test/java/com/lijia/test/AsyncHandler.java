package com.lijia.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * <pre>
 * 简单异步处理示例.
 * </pre>
 * 
 * @author F.Fang
 * @version $Id: AsyncHandler.java, v 0.1 2014年10月23日 下午11:37:54 F.Fang Exp $
 */
public class AsyncHandler {

    /**
     * 控制资源释放.
     */
    private CountDownLatch latch;

    /**
     * 处理完成标识.
     */
    private volatile boolean handleFinish;

    /**
     * 消息写入本地文件完成.
     */
    private volatile boolean sendFinish;

    /**
     * 阻塞队列.
     */
    private BlockingQueue<String> queue;

    private BufferedWriter bw;

    public AsyncHandler(CountDownLatch latch) {
        this.latch = latch;
        /**
         * 使用链表实现.
         */
        queue = new LinkedBlockingQueue<String>();
        File file = new File("E:/hello.txt");
        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handle() {
        // 模拟性能瓶颈的执行过程,3s处理一条消息.
        new Thread() {
            public void run() {
                while (!handleFinish) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e1) {
                        // 不做处理.
                    }
                    String s = queue.peek();
                    if (s != null) {
                        queue.poll();
                        try {
                            bw.write(s);
                            bw.newLine();
                        } catch (IOException e) {
                        }
                    }
                    // 若队列为空并且消息发送完成.
                    if (queue.isEmpty() && sendFinish) {
                        // 计数器1->0
                        latch.countDown();
                        // 让处理过程结束.
                        handleFinish = true;
                        break;
                    }
                }
            }

        }.start();

    }

    /**
     * 
     * <pre>
     * 给出消息发送完成的标识.
     * </pre>
     *
     */
    public void sendFinish() {
        sendFinish = true;
    }

    /**
     * 
     * <pre>
     * 资源释放.
     * </pre>
     *
     */
    public void release() {
        System.out.println("release!");
        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                // TODO 打印日志.
            }
        }
        //其实使用queue = null就够了.
        if (queue != null) {
            queue.clear();
            queue = null;
        }
    }

    /**
     * 
     * <pre>
     * 往队列发送消息.
     * </pre>
     *
     * @param text
     */
    public void sendMsg(String text) {
        if (text != null && !text.isEmpty()) {
            queue.add(text);
        }
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        AsyncHandler handler = new AsyncHandler(latch);
        handler.handle();

        // 做一次检查.
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String text = scanner.next();
            // 若用户选择退出.
            if ("exit".equals(text)) {
                // 表示消息已经发送完成.
                handler.sendFinish();
                break;
            }
            handler.sendMsg(text);
        }

        try {
            // 阻塞主线程等待消息写入到本地文件完成.
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 释放资源 文件流,队列.
        handler.release();
        // 关闭控制台输入.
        scanner.close();
    }

}