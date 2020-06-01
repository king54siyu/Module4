/**
 * 编程实现UserMessage类
 * @author：陈杨
 */

package homework4;

import java.io.Serializable;

public class UserMessage implements Serializable {

    private static final long serialVersionUID = -5814716593800822421L;

    // 自定义成员变量
    private String type;
    private User user;

    public UserMessage() {
    }

    public UserMessage(String type, User user) {
        this.type = type;
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "type='" + type + '\'' +
                ", " + user.toString() +
                '}';
    }
}
