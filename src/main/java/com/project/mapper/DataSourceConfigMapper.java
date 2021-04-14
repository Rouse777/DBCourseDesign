package com.project.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DataSourceConfigMapper {

    @Select("select @@session.max_connections")
    Map<String, Map<String, Long>> getMax_connections();

    @Select("set session max_connections = #{value}")
    void setMax_connections(@Param("value") long value);

    @Select("select @@session.wait_timeout")
    Map<String, Map<String, Long>> getWait_timeout();

    @Select("set session wait_timeout = #{value}")
    void setWait_timeout(@Param("value") long value);

    @Select("select @@session.innodb_buffer_pool_size")
    Map<String, Map<String, Long>> getbuffer_size();

    @Select("SELECT partition_name FROM information_schema.partitions WHERE table_name = #{name}")
    List<String> getPartition(@Param("name") String tableName);

    @Select("set session innodb_buffer_pool_size = #{value}")
    void setBuffer_size(@Param("value") long value);
}
