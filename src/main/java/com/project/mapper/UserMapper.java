package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<UserPO> {
    @Select("select `role` from user where `username` = #{username}")
    String selectRoleByName(@Param("username") String username);

}
