package com.restaurant.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
/**
 * 供应商
 * @TableName supply
 */
@TableName(value ="supply")
@Data
public class Supply implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 供应商名字
     */
    private String supplierName;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 联系人邮件
     */
    private String email;
    /**
     * 供应商地址信息
     */
    private String address;
    /**
     * 备注或供应商附加信息
     */
    private String remark;
    /**
     * 合作开始时间
     */
    private Date startDate;
    /**
     * 合作结束时间
     */
    private Date endDate;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 是否删除
     */
    private Integer isDelete;
    /**
     * 员工id
     */
    private Long employeeId;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}