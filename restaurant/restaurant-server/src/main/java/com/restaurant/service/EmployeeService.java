package com.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.EmployeeDTO;
import com.restaurant.dto.EmployeeLoginDTO;
import com.restaurant.entity.Employee;

public interface EmployeeService extends IService<Employee> {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 更改员工启禁用状态
     * @param status
     * @param id
     * @return
     */
    Void noUse(Integer status,Long id);

    /**
     * 跟新用户信息
     * @param employeeDTO
     * @return
     */
    Integer updateEmployee(EmployeeDTO employeeDTO);

    String getUsername(Long updateUserID);
}
