package homework4;

/**
 * 编程实现年龄异常类
 * @author: 陈杨
 */

public class AgeException extends Exception {

    static final long serialVersionUID = 7818375828146090155L;

    public AgeException() {
    }

    public AgeException(String message) {
        super(message);
    }
}
