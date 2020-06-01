/**
 * 编程实现学生管理系统和测试
 * @author: 陈杨
 */

package homework4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentSystemTest {

    // 自定义方法，用于加载相应的学生信息
    private static void load(ArrayList<Student> list) {
        try {
            File file = new File("/Users/francischen/Documents/JAVAlagou/HW/src/homework4/student.txt");
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                list.add((Student) ois.readObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void view() {

        // 生成选择的界面
        System.out.println("---------------------------------------");
        System.out.println("欢迎使用学生管理的系统，请按序号选择相应的功能！");
        System.out.print("1.添加学生\t\t");
        System.out.print("2.删除学生\n");
        System.out.print("3.修改学生\t\t");
        System.out.print("4.查找学生\n");
        System.out.print("5.得到学生信息\t");
        System.out.print("6.退出系统\n");
        System.out.println("---------------------------------------");
    }

    public static void main(String[] args) {

        // 生成一个的List用来储存学生的信息
        ArrayList<Student> system = new ArrayList<>();

        // 生成选择的界面
        Scanner sc = new Scanner(System.in);
        System.out.println("系统载入...");
        load(system);
        for (;;) {
            view();
            int choice = sc.nextInt();
            if (1 <= choice && choice <= 4) {
                System.out.println("请输入你要进行操作的学生的信息，包括学号，姓名和年龄，用空格隔开。");
                String id = sc.next();
                String name = sc.next();
                int age = sc.nextInt();
                if (1 == choice) {
                    System.out.println("正在进行添加中...");
                    try {
                        system.add(new Student(id, name, age));
                        System.out.println("添加成功！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("添加失败！无法添加！");
                    }
                } else if (2 == choice) {
                    System.out.println("正在进行删除中...");
                    try {
                        system.remove(new Student(id, name, age));
                        System.out.println("删除成功！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("无法删除！");
                    }
                } else if (3 == choice) {
                    // 获取学生在系统中的序号，并提示用户选择修改的内容
                    try {
                        int index = system.indexOf(new Student(id, name, age));
                        System.out.println("请选择你要修改的内容，在学号，姓名和年龄中进行选择！");
                        String ch = sc.next();
                        if ("学号".equals(ch)) {
                            System.out.println("请输入新的学号！");
                            String nid = sc.next();
                            system.get(index).setId(nid);
                        } else if ("姓名".equals(ch)) {
                            System.out.println("请输入新的姓名！");
                            String nname = sc.next();
                            system.get(index).setName(nname);
                        } else {
                            System.out.println("请输入新的年龄！");
                            int nage = sc.nextInt();
                            system.get(index).setAge(nage);
                        }
                        System.out.println("修改成功！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("无法修改！");
                    }
                } else {
                    System.out.println("正在查找中...");
                    try {
                        boolean b = system.contains(new Student(id, name, age));
                        System.out.println("系统是否存在的学生 " + b);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("无法查找！");
                    }
                }
            } else if (5 == choice) {
                for (Student st : system) {
                    System.out.println("学生信息是： " + st.toString());
                }
            } else if (6 == choice) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/francischen/Documents/JAVAlagou/HW/src/homework4/student.txt"));
                    for (Student st : system) {
                        oos.writeObject(st);
                        System.out.println("写入成功！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("谢谢使用！");
                break;
            }
        }
    }
}
