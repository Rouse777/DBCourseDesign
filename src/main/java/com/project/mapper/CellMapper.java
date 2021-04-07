package com.project.mapper;

import com.project.po.Cell;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 小区/基站工参表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
public interface CellMapper extends BaseMapper<Cell> {
@Select("select sector_name from cell")
    List<String> selectSectorNames();
}
