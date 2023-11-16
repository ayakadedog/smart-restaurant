package com.restaurant.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.SetmealDTO;
import com.restaurant.entity.Setmeal;
import com.restaurant.vo.DishItemVO;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

     Long getUpdateUserID(String name);

    Void noUse(Integer status, Long id);
    String getByName(String name);

    /**
     * 新增
     * @param setmealDto
     * @return
     */
    Void saveWithDish(SetmealDTO setmealDto);

    /**
     * 通过id查
     * @param ids
     * @return
     */
    SetmealDTO getByIdWithDish(Long ids);

    /**
     * 跟新
     * @param setmealDto
     */
    void updateWithDish(SetmealDTO setmealDto);

    /**
     * 删除
     * @param ids
     */
    void deleteWithDish(List<Long> ids);


    //TODO
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

}
