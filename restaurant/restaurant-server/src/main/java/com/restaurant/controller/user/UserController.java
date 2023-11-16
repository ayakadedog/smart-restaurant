package com.restaurant.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.constant.JwtClaimsConstant;
import com.restaurant.constant.MessageConstant;
import com.restaurant.constant.PasswordConstant;
import com.restaurant.constant.StatusConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.*;
import com.restaurant.entity.Employee;
import com.restaurant.entity.User;
import com.restaurant.mapper.EmployeeMapper;
import com.restaurant.properties.JwtProperties;
import com.restaurant.result.Result;
import com.restaurant.service.EmployeeService;
import com.restaurant.service.UserService;
import com.restaurant.utils.JwtUtil;
import com.restaurant.vo.EmployeeLoginVO;
import com.restaurant.vo.UserLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 */
@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){

        User user = userService.wxLogin(userLoginDTO);

        //为微信用户生成jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);

        UserLoginVO userLoginVo = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

//        UserLoginVO userLoginVO = new UserLoginVO();
//        userLoginVO.setId(user.getId());
//        userLoginVO.setOpenid(user.getOpenid());
//        userLoginVO.setToken(token);
        return Result.success(userLoginVo);
    }

}
