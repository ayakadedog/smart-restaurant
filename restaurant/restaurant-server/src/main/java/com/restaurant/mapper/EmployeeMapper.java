package com.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.restaurant.entity.Employee;
import com.restaurant.enumeration.OperationType;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Update("update employee set status = #{status} where id = #{id}")
    Integer updateStatusById(Integer status,Long id);

    @Update("update employee set password = #{password} where id = #{id}")
    Integer updatePasswordByIdInteger(String password,Long id);
    Integer updateEmployee(Employee employee);

    @Select("select username from employee where id = #{updateUserID}")
    String getUsername(Long updateUserID);
}
