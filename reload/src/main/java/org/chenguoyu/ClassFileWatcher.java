package org.chenguoyu;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * Class文件监视线程类，用来检测class文件更新
 */
public class ClassFileWatcher extends Thread {
    /**
     * 监听的文件
     */
    private File classFile;
    /**
     * 最后修改时间 (纳秒)
     */
    private long lastModified;
    /**
     *
     */
    private Instrumentation instrumentation;
    /**
     * 是否是第一次运行
     */
    private boolean firstRun = true;

    ClassFileWatcher(Instrumentation instrumentation, File classFile) {
        this.classFile = classFile;
        this.instrumentation = instrumentation;
        lastModified = classFile.lastModified();
    }

    @Override
    public void run() {
        // 循环检测
        while (true) {
            // 判断是否第一次运行，或者文件最后的修改时间与上一次时间不一致时
            if (firstRun || (lastModified != classFile.lastModified())) {
                firstRun = false;
                // 更新文件最后修改时间
                lastModified = classFile.lastModified();
                // 重新加载class
                reDefineClass(instrumentation, classFile);
            }
            try {
                // 每隔一秒休眠一次
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重新加载Class
     *
     * @param instrumentation jvm的监视对象
     * @param classFile       类文件
     */
    private static void reDefineClass(Instrumentation instrumentation, File classFile) {
        byte[] reporterClassFile = new byte[(int) classFile.length()];
        DataInputStream in;
        try {
            // 读入class文件实例化数据输入流
            in = new DataInputStream(new FileInputStream(classFile));
            in.readFully(reporterClassFile);
            in.close();
            // 通过读入的class数据输入流实例化类定义对象
            ClassDefinition reporterDef = new ClassDefinition(Class.forName("org.chenguoyu.User"), reporterClassFile);
            // 使用jvm监视对象重新定义类
            instrumentation.redefineClasses(reporterDef);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
