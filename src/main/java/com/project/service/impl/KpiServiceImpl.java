package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.KpiMapper;
import com.project.po.Kpi;
import com.project.service.KpiService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 优化小区 2020/07/17-2020/07/19KPI 指标统计表 tbKPI 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-28
 */
@Service
public class KpiServiceImpl extends ServiceImpl<KpiMapper, Kpi> implements KpiService {

    @Override
    public List<String> listSectorName() {
        return super.listObjs(new QueryWrapper<Kpi>()
                        .select("DISTINCT SECTOR_NAME"),
                o -> (String) o);
    }

    public List<Kpi> listBySectorName(String sectorName) {
        return super.list(new QueryWrapper<Kpi>()
                .eq("SECTOR_NAME", sectorName)
                .orderByAsc("StartTime"));
    }
}
