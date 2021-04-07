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
 *  MRO 测量报告数据表
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tbmrodata")
@ApiModel(value="Mrodata对象", description=" MRO 测量报告数据表")
public class Mrodata implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "测量时间点")
    @TableField("TimeStamp")
    private String TimeStamp;

    @ApiModelProperty(value = "服务小区/主小区 ID")
    @TableField("ServingSector")
    private String ServingSector;

    @ApiModelProperty(value = "干扰小区 ID")
      @TableId(value = "InterferingSector", type = IdType.AUTO)
    private String InterferingSector;

    @ApiModelProperty(value = "服务小区参考信号接收功率 RSRP")
    @TableField("LteScRSRP")
    private Float LteScRSRP;

    @ApiModelProperty(value = "干扰小区参考信号接收功率 RSRP")
    @TableField("LteNcRSRP")
    private Float LteNcRSRP;

    @ApiModelProperty(value = "干扰小区频点")
    @TableField("LteNcEarfcn")
    private Integer LteNcEarfcn;

    @ApiModelProperty(value = "干扰小区 PCI")
    @TableField("LteNcPci")
    private Integer LteNcPci;


}
