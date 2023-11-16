package com.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.UserLoginDTO;
import com.restaurant.entity.User;
import com.restaurant.mapper.UserMapper;
import org.apache.ibatis.annotations.Select;

import javax.annotation.Resource;
import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 微信用户登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);


    void changeUserMessage();

}
