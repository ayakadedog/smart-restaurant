package com.restaurant.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.dto.EmployeePageQueryDTO;
import com.restaurant.dto.UserDTO;
import com.restaurant.entity.Employee;
import com.restaurant.entity.User;
import com.restaurant.mapper.OrderMapper;
import com.restaurant.mapper.UserMapper;
import com.restaurant.result.Result;
import com.restaurant.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

@RestController("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private OrderMapper orderMapper;

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<Page> page(EmployeePageQueryDTO employeePageQueryDTO ){

        userService.changeUserMessage();

        String name = employeePageQueryDTO.getName();
        Integer page = employeePageQueryDTO.getPage();
        Integer pageSize = employeePageQueryDTO.getPageSize();

        Page<User> pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), User::getName, name);
        queryWrapper.orderByAsc(User::getCreateTime);

        userService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }


    /**
     * 更新描述
     * @param id
     * @param description
     * @return
     */
    @GetMapping("/update")
    @ApiOperation("分页查询")
    public Result<Void> updateChange(Long id,String description ){

        User user = userService.getById(id);
        user.setDescription(description);
        userService.updateById(user);

        return Result.success();
    }

}
