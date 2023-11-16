package com.restaurant.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private String phone;

    private String preferences;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
