package com.project.service.impl;

import com.project.po.Cell;
import com.project.po.Mrodata;
import com.project.mapper.MrodataMapper;
import com.project.service.MrodataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  MRO 测量报告数据表 服务实现类
 * </p>
 *
 * @since 2021-03-28
 */
@Service
public class MrodataServiceImpl extends ServiceImpl<MrodataMapper, Mrodata> implements MrodataService {
    @Override
    public void cleanAndSaveBatch(List<Mrodata> entityList) {
        List<Mrodata> res = new ArrayList<>();
        for (Mrodata entity : entityList) {
            if (isValid(entity)) res.add(entity);
        }
        super.saveOrUpdateBatch(res);
    }
    private boolean isValid(Mrodata mroData){
        String timeStamp = mroData.getTimeStamp();
        String servingSector = mroData.getServingSector();
        String interferingSector = mroData.getInterferingSector();
        if(timeStamp==null||servingSector==null||interferingSector==null){
            return false;
        }
        return true;
    }
}
