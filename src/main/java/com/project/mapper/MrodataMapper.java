package com.project.mapper;

import com.project.po.Kpi;
import com.project.po.Mrodata;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  MRO 测量报告数据表 Mapper 接口
 * </p>
 *
 * @since 2021-03-28
 */
public interface MrodataMapper extends BaseMapper<Mrodata> {
    void insertOrUpdateBatch(@Param("list") List<Mrodata> list);
}
