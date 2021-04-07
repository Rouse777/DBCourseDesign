package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.po.Cell;
import com.project.mapper.CellMapper;
import com.project.service.CellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 小区/基站工参表 服务实现类
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@Service
public class CellServiceImpl extends ServiceImpl<CellMapper, Cell> implements CellService {

    @Override
    public Cell getBySectorName(String sectorName) {
        return super.getOne(new QueryWrapper<Cell>()
                .eq("SECTOR_NAME",sectorName));
    }

    @Override
    public List<String> listSectorNames() {
        return baseMapper.selectSectorNames();
    }

    @Override
    public List<Integer> listEnodebid() {
        return super.listObjs(new QueryWrapper<Cell>()
                .select("DISTINCT ENODEBID"), o -> (Integer) o);
    }
    @Override
    public List<String> listEnodebName() {
        return super.listObjs(new QueryWrapper<Cell>()
                .select("DISTINCT ENODEB_NAME"), o -> (String) o);
    }

    @Override
    public List<Cell> listByEnodebName(String enodebName) {
        return super.list(new QueryWrapper<Cell>()
                .eq("ENODEB_NAME",enodebName));
    }
    @Override
    public List<Cell> listByEnodebid(Integer enodebid) {
        return super.list(new QueryWrapper<Cell>()
                .eq("ENODEBID",enodebid));
    }
}
