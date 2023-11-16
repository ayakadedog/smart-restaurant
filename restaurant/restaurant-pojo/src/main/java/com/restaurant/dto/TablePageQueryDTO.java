package com.restaurant.dto;
import lombok.Data;

@Data
public class TablePageQueryDTO {
    private int page;

    private int pageSize;

    private Long id;
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
