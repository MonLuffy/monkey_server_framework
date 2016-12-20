package com.luffy.framework.logger;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2  {
    private static Logger logger = LogManager.getLogger();
    public static Logger log() {
        return logger;
    }
}
