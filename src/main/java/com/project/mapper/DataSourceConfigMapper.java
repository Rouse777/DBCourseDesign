package com.project.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DataSourceConfigMapper {

    @Select("select @@session.max_connections")
    public Map<String,Map<String,Long>> getMax_connections();

    @Select("select @@session.wait_timeout")
    public Map<String,Map<String,Long>> getWait_timeout();


    @Select("select @@session.innodb_buffer_pool_size")
    public Map<String,Map<String,Long>> getbuffer_size();

    @Select("SELECT partition_name FROM information_schema.partitions WHERE table_name = '${name}'")
    public List<String> getPartition(@Param("name") String name);


    @Select("set session max_connections = ${value}")
    public void setMax_connections(@Param("value") long value);

    @Select("set session wait_timeout = ${value}")
    public void setWait_timeout(@Param("value") long value);

    @Select("set session innodb_buffer_pool_size = ${value}")
    public void setBuffer_size(@Param("value") long value);




}
