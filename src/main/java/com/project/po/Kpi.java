package com.project.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优化小区 2020/07/17-2020/07/19KPI 指标统计表 tbKPI
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kpi")
@ApiModel(value="Kpi对象", description="优化小区 2020/07/17-2020/07/19KPI 指标统计表 tbKPI")
public class Kpi implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "起始时间")
      @TableId(value = "StartTime", type = IdType.AUTO)
    private String StartTime;

    @ApiModelProperty(value = "网元/基站名称")
    @TableField("ENODEB_NAME")
    private String enodebName;

    @ApiModelProperty(value = "小区描述")
    @TableField("SECTOR_DESCRIPTION")
    private String sectorDescription;

    @ApiModelProperty(value = "小区名称")
    @TableField("SECTOR_NAME")
    private String sectorName;

    @ApiModelProperty(value = "RCC 连接建立完成次数")
    @TableField("RCCConnSUCC")
    private Integer RCCConnSUCC;

    @ApiModelProperty(value = "RCC 连接请求次数")
    @TableField("RCCConnATT")
    private Integer RCCConnATT;

    @ApiModelProperty(value = "RCC 连接成功率")
    @TableField("RCCConnRATE")
    private Float RCCConnRATE;

    @ApiModelProperty(value = "E-RAB 连接建立完成次数")
    @TableField("ERABConnSUCC")
    private Integer ERABConnSUCC;

    @ApiModelProperty(value = "E-RAB 连接请求次数")
    @TableField("ERABConnATT")
    private Integer ERABConnATT;

    @ApiModelProperty(value = "E-RAB连接成功率")
    @TableField("ERABConnRATE")
    private Float ERABConnRATE;


}
