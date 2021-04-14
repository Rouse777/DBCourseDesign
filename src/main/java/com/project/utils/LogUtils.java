package com.project.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.FastDateFormat;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.UUID;


@Slf4j
public class LogUtils {
    private static final String TIME_LOG_ID =
            FastDateFormat.getInstance("yyyy-MM-dd HH-mm-ss").format(new Date());
    private static final String UUID_LOG_ID = UUID.randomUUID().toString();
    public static final String LOG_FILE = "/log/log_clean_" + UUID_LOG_ID + ".txt";
    private static PrintStream out;

    public static void logObj(Object data) {
        if (isOutAvailable()) {
            out.println(data);
            out.flush();
        }
    }

    private static boolean isOutAvailable() {
        if (out == null) {
            try {
                out = new PrintStream(LOG_FILE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
