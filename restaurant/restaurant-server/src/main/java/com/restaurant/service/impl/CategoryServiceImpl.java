package com.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.constant.MessageConstant;

import com.restaurant.entity.Category;
import com.restaurant.entity.Dish;
import com.restaurant.exception.DeletionNotAllowedException;
import com.restaurant.mapper.CategoryMapper;
import com.restaurant.mapper.DishMapper;
import com.restaurant.mapper.SetmealMapper;
import com.restaurant.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>  implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private DishMapper dishMapper;
    @Resource
    private SetmealMapper setmealMapper;

    @Resource
    private DishServiceImpl dishService;
    /**
     * @param status
     * @param id
     * @return
     */
    @Override
    public Void noUse(Integer status, Long id) {

        categoryMapper.updateStatusById(status,id);
        if (status == 1) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("category_id", id);
            List<Dish> dishList = dishService.list(queryWrapper);
//        dishList.stream().forEach(System.out::println);
            dishList.stream().forEach(dish -> {
                Integer dishStatus = dish.getStatus();
                Long dishId = dish.getId();
                if (dishStatus == 0){
                    Integer status1 = 1;
                    dishMapper.updateStatusById(status1,dishId);
                }
            });
        }
        return null;
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void remove(Long id) {

       Integer count1 = dishMapper.countByCategoryId(id);
        if (count1 > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        int count2 = setmealMapper.countByCategoryId(id);
        if (count2 > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        super.removeById(id);
    }

    @Override
    public String getByName(String name) {
        Category category = categoryMapper.getByUsername(name);
        if (category == null)
            return null;
        return category.getName();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Override
    public List<Category> list(Integer type) {

        return categoryMapper.list(type);

    }

}
