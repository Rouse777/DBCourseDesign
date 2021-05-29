package com.project.utils;

import java.io.IOException;

public class GenerateNetwork {


    public static void Generate() throws Exception {
        String path=System.getProperty("user.dir");
        String[] args1 = new String[] {"python3",path+"/src/main/java/com/project/python/3-9.py"};
        Process proc=Runtime.getRuntime().exec(args1);
        proc.waitFor();

    }

}
