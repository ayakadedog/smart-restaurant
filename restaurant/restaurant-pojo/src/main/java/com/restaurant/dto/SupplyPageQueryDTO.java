package com.restaurant.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SupplyPageQueryDTO {
    /**
     * 页数
     */
    private int page;
    /**
     * 每页数量
     */
    private int pageSize;
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
}
