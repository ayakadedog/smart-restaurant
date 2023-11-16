package com.restaurant.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryVO implements Serializable {
    private Long id;
    //库存名称
    private String name;
    //库存分类id
    private Long categoryId;
    //库存价格
    private BigDecimal price;
    //图片
    private String image;
    //描述信息
    private String description;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedTime;
    //分类名称
    private String categoryName;
    //过期时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    //采购价格
    private BigDecimal purchasePrice;
    //库存数量
    private int amount;
    //采购地点
    private String purchasePlace;
}
