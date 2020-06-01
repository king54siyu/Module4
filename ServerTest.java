/**
 * 编程实现服务器用于接受和回复USERMESSAGE类型
 * @author:陈杨
 */

package homework4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream os = null;
        try {
            serverSocket = new ServerSocket(8888);
            // 服务一直不断的等待着别人
            while (true) {
                System.out.println("服务器等待别人来骚扰中...");
                socket = serverSocket.accept();
                System.out.println("服务器与" + socket.getInetAddress() + "成功的在一起啦！");
                ois = new ObjectInputStream(socket.getInputStream());
                os = new ObjectOutputStream(socket.getOutputStream());
                Object object = ois.readObject();
                if (object instanceof UserMessage) {
                    UserMessage userMessage = (UserMessage)object;
                    System.out.println("接受到的UserMessage是 " + userMessage.toString());
                    if ("admin".equals(userMessage.getUser().getUsername()) && "123456".equals(userMessage.getUser().getPassword())) {
                        userMessage.setType("Success!");
                        os.writeObject(userMessage);
                    } else {
                        userMessage.setType("Fail!");
                        os.writeObject(userMessage);
                    }
                    System.out.println("消息验证已经传回！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/*
一些小经验：
（1）注意java.io.CorruptedStream.Exception 要用配套的方法读和写。
 */
