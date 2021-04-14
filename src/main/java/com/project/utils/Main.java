package com.project.utils;

import com.project.po.Cell;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{
        BigFileReader.Builder builder = new BigFileReader.Builder("D:\\Desktop\\tbCell.csv",new Csv_handle(),"Cell");

        builder.withTreahdSize(10)
                .withCharset("utf-8")
                .withBufferSize(1024*1024).withBulkSize(1000);

        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
    }

}
