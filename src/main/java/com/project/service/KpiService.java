package com.project.service;

import com.project.po.Kpi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优化小区 2020/07/17-2020/07/19KPI 指标统计表 tbKPI 服务类
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
public interface KpiService extends IService<Kpi> {
    List<String> listSectorName();
    List<Kpi> listBySectorName(String sectorName);
}
