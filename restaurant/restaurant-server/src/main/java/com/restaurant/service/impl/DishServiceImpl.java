package com.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.constant.StatusConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.DishDTO;
import com.restaurant.entity.Dish;
import com.restaurant.entity.DishFlavor;
import com.restaurant.mapper.DishFlavorMapper;
import com.restaurant.mapper.DishMapper;
import com.restaurant.mapper.SetmealDishMapper;
import com.restaurant.mapper.SetmealMapper;
import com.restaurant.service.DishFlavorService;
import com.restaurant.service.DishService;
import com.restaurant.service.SetmealDishService;
import com.restaurant.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>  implements DishService {
    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorService dishFlavorService;
    @Resource
    private DishFlavorMapper dishFlavorMapper;
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealDishMapper setmealDishMapper;

    /**
     * @param status
     * @param id
     * @return
     */
    @Override
    public Void noUse(Integer status, Long id) {

        //todo 已经修改
        dishMapper.updateStatusById(status,id);
        Dish dish = dishMapper.selectById(id);
        //通过菜品id去找到套餐id，然后修改套餐id
        List<Long> longs = setmealDishMapper.selectByDishId(dish.getId());
        for (int i = 0; i < longs.size(); i++) {
            setmealMapper.updateStatusById(0, longs.get(i));
        }

        return null;
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish=Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDTO dishDto, Long dishId) {

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((add) -> {
            add.setDishId(dishId);
            return add;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 删除或批量删除菜品
     * @param list
     */
    @Override
    @Transactional
    public List<DishDTO> deleteWithFlavor(List<Long> list) {

        List<DishDTO> dishDtoList= list.stream().map((ids) -> {
            DishDTO dishDto = new DishDTO();

            Dish byId = this.getById(ids);

            this.removeById(ids);

            BeanUtils.copyProperties(byId,dishDto);

            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,ids);

            dishFlavorService.remove(queryWrapper);

            return dishDto;
        }).collect(Collectors.toList());

        return dishDtoList;

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public DishDTO getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);

        DishDTO dishDto = new DishDTO();
        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /**
     * 信息修改
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDto) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        dish.setUpdateUser(BaseContext.getCurrentId());
        dish.setUpdateTime(LocalDateTime.now());
        this.updateById(dish);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public String getByName(String name) {
        Dish dish = dishMapper.getByUsername(name);
        if (dish == null)
            return null;
        return dish.getName();
    }


    //TODO
    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {

        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

}
