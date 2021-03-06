package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.PrbMapper;
import com.project.po.Prb;
import com.project.service.PrbService;
import com.project.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 优化区17日-19日每PRB干扰 查询-15分钟 服务实现类
 * </p>
 *
 * @since 2021-03-28
 */
@Service
public class PrbServiceImpl extends ServiceImpl<PrbMapper, Prb> implements PrbService {

    @Autowired
    PrbService prbService;


    @Override
    public void cleanAndSaveBatch(List<Prb> entityList) {
        List<Prb> res = new ArrayList<>();
//        System.out.println(entityList.size());
        for (Prb entity : entityList) {
            if (isValid(entity)) res.add(entity);
            else LogUtils.logObj(entity);
        }

//        System.out.println(res.size());

        baseMapper.insertOrUpdateBatch(res);
//        prbService.saveOrUpdateBatch(res);
//        System.out.println("finish saveorupdatebatch");
    }

    private boolean isValid(Prb entity) {
        //获取需要清洗的字段
        String startTime = entity.getStartTime();
        String sectorName = entity.getSectorName();
        //null判断
        if (startTime == null || sectorName == null) return false;

        return true;
    }

    @Override
    public List<String> listEnodebName() {
        return listObjs(new QueryWrapper<Prb>()
                        .select("DISTINCT ENODEB_NAME"),
                o -> (String) o);
    }

    @Override
    public List<Prb> listByEnodebName(String enodebName, String from, String to) {
        QueryWrapper<Prb> wrapper = new QueryWrapper<Prb>()
                .eq("ENODEB_NAME", enodebName)
                .orderByAsc("StartTime");
        if (!StringUtils.isEmpty(from)) {
            wrapper = wrapper.ge("StartTime", from);
        }
        if (!StringUtils.isEmpty(to)) {
            wrapper = wrapper.lt("StartTime", to);
        }
        return list(wrapper);
    }
}
