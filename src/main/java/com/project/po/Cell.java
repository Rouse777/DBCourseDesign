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
 * 小区/基站工参表
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cell")
@ApiModel(value="Cell对象", description="小区/基站工参表")
public class Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "城市/地区名称")
    @TableField("CITY")
    private String city;

    @ApiModelProperty(value = "小区 ID")
    @TableField(value = "SECTOR_ID")
    private String sectorId;

    @ApiModelProperty(value = "小区名称")
    @TableField("SECTOR_NAME")
    private String sectorName;

    @ApiModelProperty(value = "基站 ID")
    @TableField("ENODEBID")
    private Integer enodebid;

    @ApiModelProperty(value = "基站名称")
    @TableField("ENODEB_NAME")
    private String enodebName;

    @ApiModelProperty(value = "小区配置的频点编号")
    @TableField("EARFCN")
    private Integer earfcn;

    @ApiModelProperty(value = "物理小区标识(PHYCELLID)")
    @TableField("PCI")
    private Integer pci;

    @ApiModelProperty(value = "主同步信号标识")
    @TableField("PSS")
    private Integer pss;

    @ApiModelProperty(value = "辅同步信号标识")
    @TableField("SSS")
    private Integer sss;

    @ApiModelProperty(value = "跟踪区编码")
    @TableField("TAC")
    private Integer tac;

    @ApiModelProperty(value = "设备厂家")
    @TableField("VENDOR")
    private String vendor;

    @ApiModelProperty(value = "小区所属基站的经度")
    @TableField("LONGITUDE")
    private Float longitude;

    @ApiModelProperty(value = "小区所属基站的纬度")
    @TableField("LATITUDE")
    private Float latitude;

    @ApiModelProperty(value = "基站类型")
    @TableField("STYLE")
    private String style;

    @ApiModelProperty(value = "小区天线方位角")
    @TableField("AZIMUTH")
    private Float azimuth;

    @ApiModelProperty(value = "小区天线高度")
    @TableField("HEIGHT")
    private Float height;

    @ApiModelProperty(value = "小区天线电下倾角")
    @TableField("ELECTTILT")
    private Float electtilt;

    @ApiModelProperty(value = "小区天线机械下倾角")
    @TableField("MECHTILT")
    private Float mechtilt;

    @ApiModelProperty(value = "总下倾角")
    @TableField("TOTLETILT")
    private Float totletilt;


}
