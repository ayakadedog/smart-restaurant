package com.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.EmployeeDTO;
import com.restaurant.dto.EmployeeLoginDTO;
import com.restaurant.entity.Category;
import com.restaurant.entity.Employee;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 更改分类启禁用状态
     * @param status
     * @param id
     * @return
     */
    Void noUse(Integer status,Long id);

    /**
     * 删除分类
     * @param id
     */
     void remove(Long id);

    /**
     * 通过名字查
     * @param name
     * @return
     */
    String getByName(String name);

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);

}
