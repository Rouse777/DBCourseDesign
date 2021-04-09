package com.project.utils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{
        BigFileReader.Builder builder = new BigFileReader.Builder("D:\\Desktop\\8. tbATUHandOver.csv",new IHandle() {

            @Override
            public void handle(List<String> lines,String fields) {
                //System.out.println(line);
                //increat();
                System.out.println(fields);
                System.out.println(lines.size());

            }
        },"Cell");

        builder.withTreahdSize(10)
                .withCharset("utf-8")
                .withBufferSize(1024*1024).withBulkSize(120);

        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
    }

}
