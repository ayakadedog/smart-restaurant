package com.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Long id;
    private String name;
    private BigDecimal purchasePrice;
    private int amount;
    private String purchasePlace;
    private String image;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
    private int expirationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String description;
    private Long categoryId;
    private String changeName;
    private LocalDateTime changeDate;
    private String changePurpose;
    private String alertName;
    private Date alertExpirationDate;
    private int alertTime = 2;
    private String alertWord;
    private LocalDateTime modifiedTime;
    private Long createdUser;
    private LocalDateTime createdTime;
    private Long modifiedUser;

}
