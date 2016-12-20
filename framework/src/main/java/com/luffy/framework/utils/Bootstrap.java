package com.luffy.framework.utils;

import com.luffy.framework.config.Env;
import com.luffy.framework.file.CheckFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Bootstrap {
    private static final String LIB = "lib";
    private static final String EXT = LIB + File.separatorChar + "/ext";

    private final boolean debug;
    private final String module;

    private Object daemon = null;
    private ClassLoader libClassLoader;
    private ClassLoader extLibClassLoader;

    public Bootstrap(String module) {
        this.module = module;
        this.debug = false;
    }

    public Bootstrap(String module, boolean debug) {
        this.module = module;
        this.debug = debug;
    }

    public void start() throws Exception {
        if (daemon == null) {
            //获取，检查，设置应用目录变量
            setHome();

            getExLibClassLoader();
            getLibClassLoader();

            // 设置主线程的classloader
            Thread.currentThread().setContextClassLoader(libClassLoader);
            daemon = libClassLoader.loadClass("com.luffy."+module+".Daemon").newInstance();
        }

//        Method method = daemon.getClass().getMethod("start", (Class[]) null);
//        method.invoke(daemon, (Object[]) null);
    }


    private void setHome() {
        String appPath = System.getProperty(Env.HOME);
        if (appPath == null) {
            // 用户的当前工作目录
            File workDir = new File(System.getProperty("user.dir"));

            // 判断是否在lib目录中 来判别当前环境
            if (LIB.equals(workDir.getName())) {
                workDir = workDir.getParentFile();
            }
            appPath = workDir.getAbsolutePath();
            if (debug){
                appPath = appPath + "/work";
            }

        }

        File appDir = new File(appPath);
        if (!debug) {
            checkAppDir(appDir);
        }
        // 设置应用服务根目录
        System.setProperty(Env.HOME, appDir.getAbsolutePath());
    }

    private void checkAppDir(File appDir) {
        //验证应用根目录
         CheckFile.use(appDir, "不合法的应用根目录");
        //验证LIB目录
        File libDir = new File(appDir, LIB);
        File[] files = libDir.listFiles(pathname -> pathname.getName().startsWith(module));
        if (files == null || files.length == 0 || files.length > 1) {
            throw new RuntimeException("不合法的LIB目录 没 " + module + "-xxx.jar文件: " + libDir.getAbsolutePath());
        }
    }


    private void getLibClassLoader() {
        ArrayList<URL> libList = new ArrayList<>();
        URL[] libURLs = new URL[0];
        File libDir = new File(System.getProperty(Env.HOME), LIB);

        // 添加lib目录下所有文件到classpath
        File[] files = libDir.listFiles();
        if (files != null)
            for (File lib : files) {
                try {
                    libList.add(lib.toURI().toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        libURLs = libList.toArray(libURLs);
        libClassLoader = new URLClassLoader(libURLs, extLibClassLoader);
    }

    private void getExLibClassLoader() {
        List<URL> exLibList = new ArrayList<>();
        URL[] exLibURLs = new URL[0];
        File extLibDir = new File(System.getProperty(Env.HOME), EXT);
        if (extLibDir.exists() || extLibDir.isDirectory()) {
            try {
                // 添加lib/ext目录到classpath
                exLibList.add(extLibDir.toURI().toURL());
                // 添加lib/ext目录下所有文件到classpath
                File[] files = extLibDir.listFiles();
                if (files != null)
                    for (File lib : files) {
                        exLibList.add(lib.toURI().toURL());
                    }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        exLibURLs = exLibList.toArray(exLibURLs);
        extLibClassLoader = new URLClassLoader(exLibURLs, getClass().getClassLoader());
    }

}
