package com.project.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.PrintStream;
@Slf4j
public class LogUtils {
    private static PrintStream out;
    public static final String LOG_FILE="log.txt";
    static {
        try {
            out=new PrintStream("FLOG_FILE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void logObj(Object data){
        if(isOutAvailable()){
            out.println(data);
        }
    }

    private static boolean isOutAvailable() {
        if(out==null){
            try {
                out=new PrintStream("FLOG_FILE");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
