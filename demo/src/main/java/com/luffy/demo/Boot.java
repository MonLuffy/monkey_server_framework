package com.luffy.demo;

import com.luffy.framework.config.Env;
import com.luffy.framework.utils.Bootstrap;

public class Boot {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        String module = "demo";
        Env.set(Env.MODULE, module);
        mainThread.setName(module);
        try {
            Bootstrap bootstrap = new Bootstrap(module, true);
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
