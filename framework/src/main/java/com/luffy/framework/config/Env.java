package com.luffy.framework.config;

import com.luffy.framework.file.CheckFile;

import java.io.File;

import static com.luffy.framework.logger.Log4j2.log;

public class Env {
    public static final String CONFIG = "luffy.config";
    public static final String MODULE = "luffy.module";
    public static final String LOG4J = "log4j.configurationFile";
    public static final String HOME = "luffy.home";

    public static void initialize() {
        initConfig();
        initModule();
        log().info("服务" + Env.get(MODULE) + "初始化中...");
    }

    private static String getConfigDirPath() {
        // 初始化配置目录
        String configPath = get(HOME) + File.separator + "config";
        CheckFile.use(new File(configPath), "Config目录不存在或不可读");
        return configPath;
    }

    private static void initModule() {
        String module = get(MODULE);
        String modulePath = getConfigDirPath() + File.separator + module;
        String configFilePath = modulePath + File.separator + "config.yml";
        CheckFile.use(new File(configFilePath), module + "配置文件不存在或不可读");
        String config = get(CONFIG);
        if (config == null || config.isEmpty()) {
            set(CONFIG, configFilePath);
        }
        // 初始化日志
        String logConfigPath = modulePath + File.separator + "log4j2.xml";
        CheckFile.use(new File(logConfigPath), "日志配置文件不存在或不可读");
        set(LOG4J, logConfigPath);

    }

    private static void initConfig() {
        String configFilePath = getConfigDirPath() + File.separator + "luffy.yml";
        CheckFile.use(new File(configFilePath), "主配置文件不存在或不可读");
        String config = get(CONFIG);
        if (config == null || config.isEmpty()) {
            set(CONFIG, configFilePath);
        }
    }


    public static String get(String key) {
        return System.getProperty(key);
    }

    public static String set(String key, String value) {
        return System.setProperty(key, value);
    }

}
