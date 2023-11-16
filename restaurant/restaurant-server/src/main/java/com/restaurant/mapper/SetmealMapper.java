package com.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.restaurant.entity.Dish;
import com.restaurant.entity.Setmeal;
import com.restaurant.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    //todo 已经修改
    @Select("SELECT d.status\n" +
            "FROM setmeal s\n" +
            "JOIN setmeal_dish sd ON s.id = sd.setmeal_id\n" +
            "JOIN dish d ON sd.dish_id = d.id\n" +
            "WHERE s.id = #{id};\n")
    Integer selectDishState(Long id);
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
    @Update("update setmeal set status = #{status} where id = #{id}")
    Integer updateStatusById(Integer status,Long id);
    @Select("select * from setmeal where name = #{name}")
    Dish getByUsername(String name);

    void update(Setmeal setmeal);


    //TODO
    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据条件统计套餐数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    @Select("select update_user from setmeal where name = #{name}")
    Long getUpdateUserID(String name);
}
