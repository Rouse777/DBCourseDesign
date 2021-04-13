package com.project.utils;


import lombok.SneakyThrows;

import java.util.List;

public  class CsvUtils {



    @SneakyThrows
    public static void start(String filename,String mode) {
        BigFileReader.Builder builder = new BigFileReader.Builder(filename, new Csv_handle(),mode);
        builder.withTreahdSize(10)
                .withCharset("utf-8")
                .withBufferSize(1024 * 1024).withBulkSize(300);//bulksize可以随时修改

        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();

    }


}
