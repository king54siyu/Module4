/**
 * 编程实现User类
 * @author:陈杨
 */

package homework4;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -5814716593800822421L;

    // 自定义成员变量
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
