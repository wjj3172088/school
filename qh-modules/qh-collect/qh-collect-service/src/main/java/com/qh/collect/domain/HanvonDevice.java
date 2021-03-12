package com.qh.collect.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 汉王考勤机设备管理对象 t_hanvon_device
 * 
 * @author huangdaoquan
 * @date 2020-12-29
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_hanvon_device")
public class HanvonDevice extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 主键Id */
    @TableId(value = "hd_Id", type = IdType.ID_WORKER)
    private String hdId;

    /** 设备Id */
    private String deviceId;

    /** 学校d */
    @Excel(name = "学校d")
    private String orgId;

    /** 学校名称 */
    @Excel(name = "学校名称")
    private String orgName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 安装位置 */
    @Excel(name = "安装位置")
    private String location;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long createDate;

    /** 创建人员 */
    @Excel(name = "创建人员")
    private String createUserName;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long updateDate;

    /** 修改人员 */
    @Excel(name = "修改人员")
    private String updateUserName;

    /** 设备类型  默认 0 */
    @Excel(name = "设备类型")
    private Integer deviceType;

    /** 在线状态  默认 0 */
    @Excel(name = "在线状态")
    private Integer onlineState;

    /** 备注 */
    private String remark;
}
