package homework4;

import java.io.*;
import java.net.Socket;

/**
 * 编程实现客户端
 * @author: 陈杨
 */
public class Dasheng{

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // 自定义构造器方法
    public Dasheng() throws Exception{
        this.socket = new Socket("127.0.0.1", 8080);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new readThread();
        out.println("我已经连接服务器");
        while(true){
            in = new BufferedReader(new InputStreamReader(System.in));
            String input = in.readLine();
            out.println(input);
        }
    }

    /**
     * 用于监听服务器端向客户端发送消息线程类
     */
    class readThread extends Thread{

        private BufferedReader buff;
        public readThread(){
            try {
                buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                start();
            } catch (Exception e) {
            }
        }

        @Override
        public void run() {
            try {
                while(true){
                    String result = buff.readLine();
                    if("bye".equals(result)){//客户端申请退出，服务端返回确认退出
                        System.out.println("回车退出！");
                        break;
                    } else if ("开始！".equals(result)) {
                        new FileCopyThread();
                    } else {//输出服务端发送消息
                        System.out.println(result);
                    }
                }
                in.close();
                out.close();
                System.out.println("Close!!");
                socket.close();
            } catch (Exception e) {
            }
        }
    }


    // 这个功能只是将文件进行复制并不是实际上的传输文件！
    // 实在是想不出来了
    static class FileCopyThread extends Thread {
        private int port = 9999;
        private String path = "/Users/francischen/Documents/JAVAlagou/HW/src/homework3/";


        public FileCopyThread() {
            start();
        }

        @Override
        public void run() {
            Socket socket = null;
            DataOutputStream dos = null;
            try {
                socket = new Socket("127.0.0.1", port);
                System.out.println("文件开始跑起来了！");
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                //获取服务器传过来的文件名字
                File file = new File(path + dis.readUTF());
                dos = new DataOutputStream(new FileOutputStream(file));
                //开始接收文件
                System.out.println("开始接收...");
                int length=-1;
                byte[] buff= new byte[1024];
                while((length=dis.read(buff))>0){
                    dos.write(buff, 0, length);
                }
                dos.flush();
                System.out.println("接收文件完成");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // 关闭流
                    if (dos != null) {
                        dos.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        try {
            new Dasheng();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
