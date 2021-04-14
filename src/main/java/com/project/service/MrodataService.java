package com.project.service;

import com.project.po.Cell;
import com.project.po.Mrodata;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  MRO 测量报告数据表 服务类
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
public interface MrodataService extends IService<Mrodata> {
    void cleanAndSaveBatch(List<Mrodata> data);
}
