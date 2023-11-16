package com.restaurant.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 图标信息表
* @TableName chart
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Long id;
    /**
    * 分析目标
    */
    private String goal;

    /**
    * 生成的分析结论
    */
    private String genChart;
    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 是否删除
    */
    private String description;

    /**
     * 从csv转过来的
     */
    private String reason;

}
