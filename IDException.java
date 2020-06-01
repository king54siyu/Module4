package homework4;

/**
 * 编程实现学号异常类
 * @author: 陈杨
 */

public class IDException extends Exception {

    static final long serialVersionUID = 7818375828146090155L;

    public IDException() {
    }

    public IDException(String message) {
        super(message);
    }
}
