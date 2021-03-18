package com.project.po;


import lombok.Data;

@Data
public class datasourceconfig {

    private long  innodb_buffer_pool_size;//缓存大小
    private long  wait_timeout;//空闲连接时长
    private long  max_connections;//最大连接数

}
