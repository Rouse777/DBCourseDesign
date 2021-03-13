package com.wangjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangjy.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<UserPO> {
    @Select("select `role` from user where `username` = #{username}")
    String selectRoleByName(@Param("username") String username);

}
