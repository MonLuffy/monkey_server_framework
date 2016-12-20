package com.luffy.demo;

import com.luffy.framework.utils.Bootstrap;

public class Boot {
    private static final String MODULE ="demo";
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        mainThread.setName(MODULE);
        try {
            Bootstrap bootstrap = new Bootstrap(MODULE,true);
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
