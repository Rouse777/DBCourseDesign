package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.po.Prb;
import com.project.po.Prbnew;
import com.project.mapper.PrbnewMapper;
import com.project.service.PrbnewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@Service
public class PrbnewServiceImpl extends ServiceImpl<PrbnewMapper, Prbnew> implements PrbnewService {

    @Override
    public List<String> listEnodebName() {
        return listObjs(new QueryWrapper<Prbnew>()
                        .select("DISTINCT ENODEB_NAME"),
                o -> (String) o);
    }

    @Override
    public List<Prbnew> listByEnodebName(String enodebName, String from, String to) {
        QueryWrapper<Prbnew> wrapper = new QueryWrapper<Prbnew>()
                .eq("ENODEB_NAME", enodebName)
                .orderByAsc("StartTime");
        if (!StringUtils.isEmpty(from)){
            wrapper = wrapper.ge("StartTime", from);
        }
        if (!StringUtils.isEmpty(to)) {
            wrapper = wrapper.lt("StartTime", to);
        }
        return list(wrapper);
    }
}
