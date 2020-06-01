package homework4;

/**
 * 编程实现信息类
 * @author: 陈杨
 */

public class Text {

    // 自定义成员变量
    private String name;
    private String message;

    public Text() {
    }

    public Text(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Text{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
