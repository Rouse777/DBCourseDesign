package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.CellMapper;
import com.project.po.Cell;
import com.project.service.CellService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void cleanAndSaveBatch(List<Cell> entityList) {
        List<Cell> res = new ArrayList<>();
        for (Cell entity : entityList) {
            if (isValid(entity)) res.add(entity);
        }
        super.saveOrUpdateBatch(res);
    }

    private boolean isValid(Cell cell) {
        //获取需要清洗的属性
        String sectorId = cell.getSectorId();
        String sectorName = cell.getSectorName();
        Integer enodebid = cell.getEnodebid();
        String enodebName = cell.getEnodebName();
        Integer earfcn = cell.getEarfcn();
        Integer pci = cell.getPci();
        Integer pss = cell.getPss();
        Integer sss = cell.getSss();
        Float azimuth = cell.getAzimuth();
        Float totletilt = cell.getTotletilt();
        Float electtilt = cell.getElecttilt();
        Float mechtilt = cell.getMechtilt();

        //判断null
        Float longitude = cell.getLongitude();
        Float latitude = cell.getLatitude();
        if (sectorId == null
                || sectorName == null
                || enodebid == null
                || enodebName == null
                || earfcn == null
                || pci == null
                || longitude == null
                || latitude == null
                || azimuth == null
                || totletilt == null) {
            return false;
        }

        //PCI PSS SSS

        //PCI between(0,503)
        if (pci < 0 || pci > 503) return false;
        if ( pss != null && sss != null) {
            //PSS in (0,1,2)
            if (pss < 0 || pss > 2) return false;
            //SSS between(0,167)
            if (sss < 0 || sss > 167) return false;
            //PCI= 3*SSS + PSS
            if(pci!=3*sss+pss)return false;
        }
        //Longitude between(-180,180)
        if(longitude<-180||longitude>180) return false;
        //Latitude between(-90,90)
        if(latitude<-90||latitude>90)return false;

        //TOTLETILT= ELECTTILT+ MECHTILT(浮点相等判断)
        if(Math.abs(electtilt+mechtilt-totletilt)>0.01)return false;

        return true;
    }

    @Override
    public Cell getBySectorName(String sectorName) {
        return super.getOne(new QueryWrapper<Cell>()
                .eq("SECTOR_NAME", sectorName));
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
                .eq("ENODEB_NAME", enodebName));
    }

    @Override
    public List<Cell> listByEnodebid(Integer enodebid) {
        return super.list(new QueryWrapper<Cell>()
                .eq("ENODEBID", enodebid));
    }
}
