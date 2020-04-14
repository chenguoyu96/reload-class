package org.chenguoyu;

/**
 * 程序入口类
 */
public class Main {
    public static void main(String[] args) {

        while (true) {
            System.out.println(new User("chenguoyu", 24));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
