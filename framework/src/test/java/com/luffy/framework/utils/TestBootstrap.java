package com.luffy.framework.utils;

import org.junit.Test;

import java.io.File;

public class TestBootstrap {
    @Test
    public void testBootstrap() throws Exception {
       /*
         Thread mainThread = Thread.currentThread();
        mainThread.setName("luffy");

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
       */
        File workDir = new File(System.getProperty("user.dir"));
        System.out.println(workDir.getAbsolutePath());
        //new Bootstrap("", "").start();

    }
}
