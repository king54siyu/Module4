/**
 * 编程实现客户端向服务器发送信息并且接受信息
 * @author:陈杨
 */

package homework4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest {

    public static void main(String[] args) {

        Socket socket = null;
        Scanner scanner = null;
        ObjectOutputStream os = null;
        ObjectInputStream ois = null;
        try {
            socket = new Socket("127.0.0.1", 8888);
            os = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("成功得到了宠幸！");
            System.out.println("请用英文输入你的用户名和密码，用空格隔开！");
            scanner = new Scanner(System.in);
            String username = scanner.next();
            String password = scanner.next();
            // 创建一个User引用
            User user = new User(username, password);
            UserMessage userMessage = new UserMessage("check", user);
            os.writeObject(userMessage);
            System.out.println("成功发送UserMessage信息");
            Object object = ois.readObject();
            if (object instanceof UserMessage) {
                UserMessage userMessage1 = (UserMessage)object;
                if ("Success!".equals(userMessage1.getType())) {
                    System.out.println("恭喜你成功上位！");
                }
                if ("Fail!".equals(userMessage1.getType())) {
                    System.out.println("可惜不是你，再来一次吧！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != scanner) {
                scanner.close();
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
        }
    }
}
