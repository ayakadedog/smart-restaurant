package com.restaurant.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InventoryDTO implements Serializable {
    private Long id;
    //库存名称
    private String name;

    //采购地点
    private String purchasePlace;

    //采购日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    private String purchaseTime;
    //过期时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    //采购价格
    private BigDecimal purchasePrice;
    //库存数量
    private int amount;

    private  String image;

    private String expirationTime;

}
