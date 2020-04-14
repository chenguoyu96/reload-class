package org.chenguoyu;

/**
 *  用户信息实体类
 */
public class User {
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "姓名："+name +" 年龄："+age;
    }
}
