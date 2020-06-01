package homework4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 编程实现服务器用于控制多人之间的对话和文件传输
 * @author: 陈杨
 */

public class ServerMulTest {

    // 自定义成员变量用来存储
    private static List<String> users = new ArrayList<>(); // 记录所有上线的用户
    private static List<ServerThread> serverThreads = new ArrayList<>(); // 记录为用户服务的线程
    private static LinkedList<Text> texts = new LinkedList<>(); // 记录用户发的信息
    // 自定义成员变量判断是否要转发
    private static boolean zhuanfa = false;


    // 编程实现转发消息的线程
    class SpreadThread extends Thread {

        // 自定义构造器，直接启动线程类
        public SpreadThread() {
            start();
        }

        @Override
        public void run() {
            while (true) {
                //如果消息队列没有消息则暂停当前线程，把cpu片段让出给其他线程,提高性能
                if (!zhuanfa) {
                    try {
                        Thread.sleep(500);
                        sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    continue;
                }
                // 将缓存在队列中的消息按顺序发送到各客户端，并从队列中清除。
                Text text = texts.getFirst();
                // 对所有的用户的线程遍历，如果不是自己发的消息就广播给其他人
                for (int i = 0; i < serverThreads.size(); i++) {
                    // 由于添加线程和用户是一起的，所以i所对应的用户就是i所对应的线程，可以根据这个判断是不是自己的线程
                    ServerThread thread = serverThreads.get(i);
                    if (text.getName() != users.get(i)) {
                        thread.zhuanfa(text);
                    }
                }
                texts.removeFirst();
                zhuanfa = texts.size() > 0;
            }
        }
    }

    // 自定义内部线程类用以文件传输
    // 注意：其实只是一个简单的文件复制的过程，具体怎么推动到每一个在线的用户，并没有想出来
    static class FileThread extends Thread {
        // 文件传输端口9999
        private ServerSocket fileSSocket;
        private int port = 9999;
        private String path;

        public FileThread(String path) {
            System.out.println("来吧文件!!");
            this.path = path; // 获取用户想要传的文件地址
            start();
        }

        @Override
        public void run() {
            DataInputStream dis = null;
            Socket socket = null;
            try {
                fileSSocket = new ServerSocket(port);
                socket = fileSSocket.accept();
                System.out.println("客户端已经链接文件服务");
                File file = new File(path);
                if (file.exists()) {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(new FileInputStream(file));
                    // 传送文件名字
                    dos.writeUTF(file.getName());
                    dos.flush();

                    System.out.println("开始传送文件...");
                    // 传送文件
                    int length = -1;// 读取到的文件长度
                    byte[] buff = new byte[1024];
                    // 读取文件，直到结束
                    while ((length = dis.read(buff)) > 0) {
                        dos.write(buff, 0, length);
                        dos.flush();
                    }
                    System.out.println("传送文件完成");
                } else {
                    System.out.println("文件地址不正确！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // 关闭流
                    if (dis != null) {
                        dis.close();
                    }
                    //关闭客户端口
                    if (socket != null) {
                        socket.close();
                    }
                    // 关闭服务端口
                    if (fileSSocket != null) {
                        fileSSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // 自定义内部线程类，用来创造线程为客户端进行服务
    class ServerThread extends Thread {

        // 套接和客户端进行连接
        private Socket socket;

        // 缓冲流用于获取客户端的输入
        private BufferedReader bin;

        // 打印
        private PrintWriter pout;

        // 客户端姓名
        private String username;

        // 自定义constructor
        public ServerThread(Socket socket) throws IOException {
            this.socket = socket;
            pout = new PrintWriter(socket.getOutputStream(), true);
            bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 读取
            String s = bin.readLine();
            System.out.println(s);

            start();
        }


        // 自定义成员方法记录发送的信息
        public void zhuanfa(Text text) {
            pout.println(text.getName() + "说了" + text.getMessage());
        }

        // 写成内部类方便记录和相应的用户的信息！
        public void recordText(String name, String content) {
            Text text = new Text(name, content);
            texts.addLast(text); // 记录发送的消息，用于后面转发信息

            zhuanfa = true;
        }

        // 改写run方法
        @Override
        public void run() {
            pout.println("欢迎您来撩我！请输入你的用户名！");
            System.out.println(getName()); // 获取相应的线程的名称，从父类继承而来
            try {
                int times = 0;
                String content = bin.readLine();
                while (true) {
                    if ("bye".equalsIgnoreCase(content)) {
                        break;
                    }
                    if ("我要传文件".equals(content)) {
                        pout.println("请指定你文件的完整路径！");
                        String path = bin.readLine();
                        new FileThread(path);
                        pout.println("开始！");
                    }
                    if (0 == times) { // 第一次上线
                        this.username = content;
                        // 将相应的用户和线程添加到列表中方便管理！
                        users.add(username);
                        serverThreads.add(this);
                        pout.println(username + "朋友，你可以开始你的表演了～～");
                        System.out.println(username + "上线了！");
                        // 存储信息，之后发送给其他用户
                        recordText(username, "上线表演了！");
                    } else {
                        recordText(username, content);
                    }
                    times++;
                    content = bin.readLine();
                    System.out.println(username + ":" + content);
                }
                pout.println("bye");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("关闭！");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverThreads.remove(this);
                users.remove(username);
                recordText(username, "我不玩了！！");
            }
        }
    }


    public ServerMulTest() {
        ServerSocket serverSocket = null;
        try {
             serverSocket = new ServerSocket(8080);
            new SpreadThread();
            System.out.println("服务器上线了，各位来撩吧！");
            while (true) {
                Socket socket = serverSocket.accept();
                new ServerThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ServerMulTest();
    }
}
