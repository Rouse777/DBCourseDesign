package com.project.mapper;

import com.project.po.Cell;
import com.project.po.Kpi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 优化小区 2020/07/17-2020/07/19KPI 指标统计表 tbKPI Mapper 接口
 * </p>
 *
 * @since 2021-03-28
 */
public interface KpiMapper extends BaseMapper<Kpi> {
    void insertOrUpdateBatch(@Param("list") List<Kpi> list);
}
