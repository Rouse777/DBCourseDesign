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
 * 优化区17日-19日每PRB干扰 查询-15分钟
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tbprb")
@ApiModel(value="Prb对象", description="优化区17日-19日每PRB干扰 查询-15分钟")
public class Prb implements Serializable {

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

    @ApiModelProperty(value = "第0个PRB上检测到的干扰噪声的平均值 (毫瓦分贝)")
    @TableField("PRB_0")
    private Integer prb0;

    @TableField("PRB_1")
    private Integer prb1;

    @TableField("PRB_2")
    private Integer prb2;

    @TableField("PRB_3")
    private Integer prb3;

    @TableField("PRB_4")
    private Integer prb4;

    @TableField("PRB_5")
    private Integer prb5;

    @TableField("PRB_6")
    private Integer prb6;

    @TableField("PRB_7")
    private Integer prb7;

    @TableField("PRB_8")
    private Integer prb8;

    @TableField("PRB_9")
    private Integer prb9;

    @TableField("PRB_10")
    private Integer prb10;

    @TableField("PRB_11")
    private Integer prb11;

    @TableField("PRB_12")
    private Integer prb12;

    @TableField("PRB_13")
    private Integer prb13;

    @TableField("PRB_14")
    private Integer prb14;

    @TableField("PRB_15")
    private Integer prb15;

    @TableField("PRB_16")
    private Integer prb16;

    @TableField("PRB_17")
    private Integer prb17;

    @TableField("PRB_18")
    private Integer prb18;

    @TableField("PRB_19")
    private Integer prb19;

    @TableField("PRB_20")
    private Integer prb20;

    @TableField("PRB_21")
    private Integer prb21;

    @TableField("PRB_22")
    private Integer prb22;

    @TableField("PRB_23")
    private Integer prb23;

    @TableField("PRB_24")
    private Integer prb24;

    @TableField("PRB_25")
    private Integer prb25;

    @TableField("PRB_26")
    private Integer prb26;

    @TableField("PRB_27")
    private Integer prb27;

    @TableField("PRB_28")
    private Integer prb28;

    @TableField("PRB_29")
    private Integer prb29;

    @TableField("PRB_30")
    private Integer prb30;

    @TableField("PRB_31")
    private Integer prb31;

    @TableField("PRB_32")
    private Integer prb32;

    @TableField("PRB_33")
    private Integer prb33;

    @TableField("PRB_34")
    private Integer prb34;

    @TableField("PRB_35")
    private Integer prb35;

    @TableField("PRB_36")
    private Integer prb36;

    @TableField("PRB_37")
    private Integer prb37;

    @TableField("PRB_38")
    private Integer prb38;

    @TableField("PRB_39")
    private Integer prb39;

    @TableField("PRB_40")
    private Integer prb40;

    @TableField("PRB_41")
    private Integer prb41;

    @TableField("PRB_42")
    private Integer prb42;

    @TableField("PRB_43")
    private Integer prb43;

    @TableField("PRB_44")
    private Integer prb44;

    @TableField("PRB_45")
    private Integer prb45;

    @TableField("PRB_46")
    private Integer prb46;

    @TableField("PRB_47")
    private Integer prb47;

    @TableField("PRB_48")
    private Integer prb48;

    @TableField("PRB_49")
    private Integer prb49;

    @TableField("PRB_50")
    private Integer prb50;

    @TableField("PRB_51")
    private Integer prb51;

    @TableField("PRB_52")
    private Integer prb52;

    @TableField("PRB_53")
    private Integer prb53;

    @TableField("PRB_54")
    private Integer prb54;

    @TableField("PRB_55")
    private Integer prb55;

    @TableField("PRB_56")
    private Integer prb56;

    @TableField("PRB_57")
    private Integer prb57;

    @TableField("PRB_58")
    private Integer prb58;

    @TableField("PRB_59")
    private Integer prb59;

    @TableField("PRB_60")
    private Integer prb60;

    @TableField("PRB_61")
    private Integer prb61;

    @TableField("PRB_62")
    private Integer prb62;

    @TableField("PRB_63")
    private Integer prb63;

    @TableField("PRB_64")
    private Integer prb64;

    @TableField("PRB_65")
    private Integer prb65;

    @TableField("PRB_66")
    private Integer prb66;

    @TableField("PRB_67")
    private Integer prb67;

    @TableField("PRB_68")
    private Integer prb68;

    @TableField("PRB_69")
    private Integer prb69;

    @TableField("PRB_70")
    private Integer prb70;

    @TableField("PRB_71")
    private Integer prb71;

    @TableField("PRB_72")
    private Integer prb72;

    @TableField("PRB_73")
    private Integer prb73;

    @TableField("PRB_74")
    private Integer prb74;

    @TableField("PRB_75")
    private Integer prb75;

    @TableField("PRB_76")
    private Integer prb76;

    @TableField("PRB_77")
    private Integer prb77;

    @TableField("PRB_78")
    private Integer prb78;

    @TableField("PRB_79")
    private Integer prb79;

    @TableField("PRB_80")
    private Integer prb80;

    @TableField("PRB_81")
    private Integer prb81;

    @TableField("PRB_82")
    private Integer prb82;

    @TableField("PRB_83")
    private Integer prb83;

    @TableField("PRB_84")
    private Integer prb84;

    @TableField("PRB_85")
    private Integer prb85;

    @TableField("PRB_86")
    private Integer prb86;

    @TableField("PRB_87")
    private Integer prb87;

    @TableField("PRB_88")
    private Integer prb88;

    @TableField("PRB_89")
    private Integer prb89;

    @TableField("PRB_90")
    private Integer prb90;

    @TableField("PRB_91")
    private Integer prb91;

    @TableField("PRB_92")
    private Integer prb92;

    @TableField("PRB_93")
    private Integer prb93;

    @TableField("PRB_94")
    private Integer prb94;

    @TableField("PRB_95")
    private Integer prb95;

    @TableField("PRB_96")
    private Integer prb96;

    @TableField("PRB_97")
    private Integer prb97;

    @TableField("PRB_98")
    private Integer prb98;

    @TableField("PRB_99")
    private Integer prb99;


}
