/**
 *  编程用于实现的删除一个指定目录下所有内容和测试
 *  @author: 陈杨
 */

package homework4;

import java.io.File;

public class DeletesTest {

    public static void deletes(String dir) {

        // 要求传入的路径是绝对路径
        File del = new File(dir);
        if (del.exists()) {
            File[] files = del.listFiles();

            // 删除当前目录下的所有文件
            if (0 != files.length) {
                for (File tf : files) {
                    if (tf.isFile()) tf.delete();
                    if (tf.isDirectory()) {
                        String absolutePath = tf.getAbsolutePath();
                        deletes(absolutePath);
                        tf.delete();
                    }
                }
            }

            // 按着顺序结构，此时可以直接删除当前目录
            del.delete();
            System.out.println("已经删除了相应的目录！");
        } else {
            System.out.println("不存在相应的目录，请重新确认！");
        }
    }

    public static void main(String[] args) {

        // 使用提前已经准备好的目录地址
        String dir = "/Users/francischen/Documents/JAVAlagou/HW/src/homework3/testcopy";
        deletes(dir);
    }
}
