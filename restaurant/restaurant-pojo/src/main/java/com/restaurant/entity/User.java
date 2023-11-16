package com.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //微信用户唯一标识
    private String openid;

    //姓名
    private String name;

    //手机号
    private String phone;

    //性别 0 女 1 男  性别当客户偏好
    private String preferences;

    //身份证号  身份证号当下单数
    private String number;

    //头像  把头像当描述用
    private String description;

    //注册时间
    private LocalDateTime createTime;
}
