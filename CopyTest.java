/**
 * 编程实现文件的拷贝
 * @author: 陈杨
 */

package homework4;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyTest implements Runnable {

    // 编程重写RUN方法
    @Override
    public void run() {
        // 确定复制的来源和目标地
        File src = new File("/Users/francischen/Documents/JAVAlagou/HW/src/homework4/testcopy");
        File dst = new File("/Users/francischen/Documents/JAVAlagou/HW/src/homework3/testcopy");

        // 调用复制的函数
        DirCopy(src, dst);

        System.out.println("完成任务，所有复制工作已经结束");
    }

    // 编程实现文件的复制
    public static void FileCopy(File srcFile, File dstFile) {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
             bis = new BufferedInputStream(new FileInputStream(srcFile));
             bos = new BufferedOutputStream(new FileOutputStream(dstFile));

            byte[] bArr = new byte[1024];
            int res = 0;
            while ((res = bis.read(bArr)) != -1) {
                bos.write(bArr, 0 , res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bis) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bos) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 编程实现自定义方法用于目录的复制
    public static void DirCopy(File src, File dst) {

        // 传入相应DIR File
        if (!dst.exists()) {
            dst.mkdir();
        }
        File[] files = src.listFiles(); // 拿到SRC中的所有文件，并做相应的判断
        for (File tf : files) {
            if (tf.isFile()) {
                System.out.println("正在进行文件" + tf.getName() + "的复制！");
                FileCopy(new File(tf.getAbsolutePath()), new File(dst + "/" + tf.getName()));
            } else {
                // 利用递归的思想直接调用当前函数
                System.out.println("进行子目录【" + tf.getName() + "】的复制！" );
                DirCopy(new File(tf.getAbsolutePath()), new File(dst + "/" + tf.getName()));
            }
        }
    }


    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 创建相应的对象
        CopyTest copyTest = new CopyTest();

        // 运行RUNNABLE对象
        executorService.execute(copyTest);

        executorService.shutdown();
    }
}

