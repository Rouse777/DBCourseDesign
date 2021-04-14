package com.project.service;

import com.project.po.Prb;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优化区17日-19日每PRB干扰 查询-15分钟 服务类
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
public interface PrbService extends IService<Prb> {
    void cleanAndSaveBatch(List<Prb> entityList);
    List<String> listEnodebName();
    List<Prb> listByEnodebName(String enodebName,String from,String to);
}
