package org.chenguoyu;

import java.io.File;
import java.lang.instrument.Instrumentation;

public class LoadAgent {
    private static final String classPath = "E:\\github\\reload-class\\common\\target\\classes\\org\\chenguoyu\\User.class";

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        try {
            // 监视的User.class所在的路径，此处为了示例演示，先直接硬编码，只监视指定目录下User.class的变化
            File f = new File(classPath);
            // 开启一个线程，用来监视文件变化
            ClassFileWatcher watcher = new ClassFileWatcher(instrumentation, f);
            watcher.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
