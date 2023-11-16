package com.restaurant.dto;
import lombok.Data;
import java.util.Date;

@Data
public class SupplyDTO {
    /**
     * 主键
     */
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


}
