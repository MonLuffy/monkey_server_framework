package com.luffy.demo;

import com.luffy.framework.config.Define;

import java.io.File;

public class Daemon {
    private static final String HOME = "com.luffy";

    public Daemon() {
        initialize();
    }
    private void initialize() {

        // 初始化配置目录
        String configPath = System.getProperty(HOME)+ File.separator + "config";
        File configDir = new File(configPath);
        if (!configDir.exists()||!configDir.canRead()) {
            throw new RuntimeException("Config目录不存在或不可读: " + configDir.getAbsolutePath());
        }

        // 初始化主配置文件
        String configFilePath = configPath + File.separator + Define.CONFIG;
        File configFile = new File(configFilePath);
        if (!configFile.exists()||!configFile.canRead()) {
            throw new RuntimeException("主配置文件不存在或不可读: " + configFile.getAbsolutePath());
        }
        String config = System.getProperty(Define.CONFIG);
        if (config == null || config.isEmpty()) {
            System.setProperty(Define.CONFIG,configFilePath);
        }

        // 初始化日志
        String logConfigPath = configPath + File.separator + Define.LOG4J;
        File logConfigFile = new File(logConfigPath);
        if (!logConfigFile.exists()||!logConfigFile.canRead()) {
            throw new RuntimeException("日志配置文件不存在或不可读: " + logConfigFile.getAbsolutePath());
        }
//        System.getProperty(Define.LOG4J);
        /*



        File logConfigFie = new File(logConfig);
        if (logConfigFie.exists()) {
            System.setProperty("logback.config", logConfigFie.getAbsolutePath());
            StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
            LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();
            loggerContext.reset();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            try {
                configurator.doConfigure(logConfigFie);
            } catch( JoranException e ) {
                LoggerFactory.getLogger(TestDaemon.class).error("初始化日志系统出错", e);
            }
            StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
        }
*/

        /*
        //初始化环境
        LoggerFactory.getLogger(TestDaemon.class).info("初始化环境中...");
        ctx = new AnnotationConfigApplicationContext();
        ((AnnotationConfigApplicationContext)ctx).register(StandaloneConfiguration.class);
        ctx.refresh();*/
    }
    public void start() {

    }

    public void stop() {
    }
}
