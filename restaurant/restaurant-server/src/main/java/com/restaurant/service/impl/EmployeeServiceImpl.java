package com.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.constant.MessageConstant;
import com.restaurant.constant.StatusConstant;
import com.restaurant.dto.EmployeeDTO;
import com.restaurant.dto.EmployeeLoginDTO;
import com.restaurant.entity.Employee;
import com.restaurant.exception.AccountLockedException;
import com.restaurant.exception.AccountNotFoundException;
import com.restaurant.exception.OrderBusinessException;
import com.restaurant.exception.PasswordErrorException;
import com.restaurant.mapper.EmployeeMapper;
import com.restaurant.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>  implements EmployeeService  {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new OrderBusinessException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 启禁用员工信息
     * @param status
     * @param id
     * @return
     */
    @Override
    public Void noUse(Integer status, Long id) {

        employeeMapper.updateStatusById(status,id);

        return null;
    }

    /**
     * 跟新员工信息
     * @param employeeDTO
     * @return
     */
    @Override
    public Integer updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employeeMapper.updateEmployee(employee);
        return null;
    }

    @Override
    public String getUsername(Long updateUserID) {
        String username = employeeMapper.getUsername(updateUserID);
        return username;
    }


//    /**
//     * 分页查询
//     *
//     * @param employeePageQueryDTO
//     * @return
//     */
//    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
//        //开始分页查询
//        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
//
//        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);//后续定义
//
//        long total = page.getTotal();
//        List<Employee> records = page.getResult();
//
//        return new PageResult(total, records);
//    }

}
