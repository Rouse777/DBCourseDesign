package com.project.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OutputMapper {

    @Select(" select COLUMN_NAME from information_schema.COLUMNS where TABLE_SCHEMA=(select database()) and TABLE_NAME=#{name} ;")
    List<String> getFields(@Param("name") String tableName);

}
