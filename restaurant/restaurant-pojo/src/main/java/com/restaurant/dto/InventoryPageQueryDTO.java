package com.restaurant.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InventoryPageQueryDTO implements Serializable {
    private int page;

    private int pageSize;
    private Integer categoryId;
    private String name;
    private String purchasePlace;
    //采购日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
    //过期时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private BigDecimal purchasePrice;
    private int amount;
}
