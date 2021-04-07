package com.project.service;

import com.project.po.Cell;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 小区/基站工参表 服务类
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
public interface CellService extends IService<Cell> {
    Cell getBySectorName(String sectorName);
    List<String> listSectorNames();
    List<Integer> listEnodebid();
    List<String> listEnodebName();
    List<Cell> listByEnodebName(String enodebName);
    List<Cell> listByEnodebid(Integer enodebid);
}
