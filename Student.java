/**
 * 编程实现学生类
 * @author: 陈杨
 */

package homework4;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {

    static final long serialVersionUID = 7818375828146090155L;

    // 自定义成员变量
    private String id;
    private String name;
    private int age;

    // 相应的方法
    public Student() {
    }

    public Student(String id, String name, int age) throws IDException, AgeException {
        setId(id);
        setName(name);
        setAge(age);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws IDException {
        if (id.length() <= 8) {
            this.id = id;
        } else {
            throw new IDException("你的学号太长了，不可以！");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws AgeException {
        if (age <= 24 && age >= 15) {
            this.age = age;
        } else {
            throw new AgeException("你的年龄不适合读书哦！");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                id.equals(student.id) &&
                name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
