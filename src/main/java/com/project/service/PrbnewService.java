package com.project.service;

import com.project.po.Prb;
import com.project.po.Prbnew;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2021-03-28
 */
public interface PrbnewService extends IService<Prbnew> {
    List<String> listEnodebName();
    List<Prbnew> listByEnodebName(String enodebName, String from, String to);
}
