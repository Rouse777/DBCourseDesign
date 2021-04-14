package com.project.mapper;

import com.project.po.Kpi;
import com.project.po.Prb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优化区17日-19日每PRB干扰 查询-15分钟 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
public interface PrbMapper extends BaseMapper<Prb> {
    void insertOrUpdateBatch(@Param("list") List<Prb> list);
}
