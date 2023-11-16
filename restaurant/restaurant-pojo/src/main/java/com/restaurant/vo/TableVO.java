package com.restaurant.vo;

import lombok.Data;

@Data
public class TableVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 员工id
     */
    private Long employeeId;

    /**
     * 桌号
     */
    private String tableNumber;

    /**
     * 餐桌容纳人数
     */
    private String seatNumber;

    /**
     * 摆放位置
     */
    private String area;

    /**
     * 餐桌类型
     */
    private String tableType;

    /**
     * 餐桌备注
     */
    private String remark;
}
