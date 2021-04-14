package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.KpiMapper;
import com.project.po.Kpi;
import com.project.po.Mrodata;
import com.project.service.KpiService;
import com.project.utils.LogUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 优化小区 2020/07/17-2020/07/19KPI 指标统计表 tbKPI 服务实现类
 * </p>
 *
 * @since 2021-03-28
 */
@Service
public class KpiServiceImpl extends ServiceImpl<KpiMapper, Kpi> implements KpiService {

    @Override
    public void cleanAndSaveBatch(List<Kpi> entityList) {
        List<Kpi> res = new ArrayList<>();
        for (Kpi entity : entityList) {
            if (isValid(entity)) res.add(entity);
            else LogUtils.logObj(entity);
        }
        baseMapper.insertOrUpdateBatch(res);
    }
    private boolean isValid(Kpi entity){
        //获取需要清洗的字段
        String sectorDescription = entity.getSectorDescription();
        String sectorName = entity.getSectorName();

        //null判断
        if(sectorDescription==null||sectorName==null)return false;

        return true;
    }
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
