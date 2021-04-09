package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.PrbMapper;
import com.project.po.Prb;
import com.project.service.PrbService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 优化区17日-19日每PRB干扰 查询-15分钟 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-28
 */
@Service
public class PrbServiceImpl extends ServiceImpl<PrbMapper, Prb> implements PrbService {

    @Override
    public List<String> listEnodebName() {
        return listObjs(new QueryWrapper<Prb>()
                        .select("DISTINCT ENODEB_NAME"),
                o -> (String) o);
    }

    @Override
    public List<Prb> listByEnodebName(String enodebName, String from, String to) {
        QueryWrapper<Prb> wrapper = new QueryWrapper<Prb>()
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