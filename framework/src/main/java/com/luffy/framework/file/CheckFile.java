package com.luffy.framework.file;

import java.io.File;

public class CheckFile {
    public static void use(File f, String error) {
        if (!f.exists() || !f.canRead()) {
            throw new RuntimeException(error+": " + f.getAbsolutePath());
        }
    }
}
