package com.project.service.impl;

import com.project.po.Cell;
import com.project.po.Mrodata;
import com.project.mapper.MrodataMapper;
import com.project.service.MrodataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.project.utils.*;

/**
 * <p>
 *  MRO 测量报告数据表 服务实现类
 * </p>
 *
 * @since 2021-03-28
 */
@Service
public class MrodataServiceImpl extends ServiceImpl<MrodataMapper, Mrodata> implements MrodataService {


    @Autowired
    MrodataService mrodataService;

    @Override
    public void cleanAndSaveBatch(List<Mrodata> entityList) {
//        long a=System.currentTimeMillis();
        List<Mrodata> res = new ArrayList<>();
        for (Mrodata entity : entityList) {
            if (isValid(entity)) res.add(entity);
            else LogUtils.logObj(entity);
        }

        baseMapper.insertOrUpdateBatch(res);
        long b=System.currentTimeMillis();
        System.out.println("插入数据："+String.valueOf(res.size())+"  "+"用时："+String.valueOf(b-CsvUtils.starttime)+" "+"线程ID："+Thread.currentThread().getName());


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
