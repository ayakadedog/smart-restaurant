package com.restaurant.entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
/**
 * 桌号
 * @TableName table
 */
@Data
public class CaterTable implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id ;
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
    @TableLogic
    private Integer isDelete;
    private static final long serialVersionUID = 1L;
}