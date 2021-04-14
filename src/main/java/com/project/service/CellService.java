package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.po.Cell;

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
    /**
     * 对传入数据进行清洗，剔除不合法数据并批量插入或更新数据库
     * @param data 要清洗的数据
     */
    void cleanAndSaveBatch(List<Cell> data);

    Cell getBySectorName(String sectorName);

    List<String> listSectorNames();

    List<Integer> listEnodebid();

    List<String> listEnodebName();

    List<Cell> listByEnodebName(String enodebName);

    List<Cell> listByEnodebid(Integer enodebid);
}
