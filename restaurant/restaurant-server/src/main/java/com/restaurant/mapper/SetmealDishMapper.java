package com.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.restaurant.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

    //todo 已经修改
    @Select("select setmeal_id from setmeal_dish where dish_id = #{dishId}")
    List<Long> selectByDishId(Long dishId);
}
