package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.po.Kpi;
import com.project.po.Prb;

import java.util.List;

/**
 * <p>
 * 优化小区 2020/07/17-2020/07/19KPI 指标统计表 tbKPI 服务类
 * </p>
 *
 * @since 2021-03-28
 */
public interface KpiService extends IService<Kpi> {
    void cleanAndSaveBatch(List<Kpi> entityList);

    List<String> listSectorName();

    List<Kpi> listBySectorName(String sectorName);
}
