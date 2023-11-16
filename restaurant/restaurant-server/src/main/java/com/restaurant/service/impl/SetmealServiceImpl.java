package com.restaurant.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.constant.MessageConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.SetmealDTO;
import com.restaurant.entity.Dish;
import com.restaurant.entity.Setmeal;
import com.restaurant.entity.SetmealDish;
import com.restaurant.exception.BusinessException;
import com.restaurant.exception.DeletionNotAllowedException;
import com.restaurant.mapper.SetmealMapper;
import com.restaurant.result.Result;
import com.restaurant.service.SetmealDishService;
import com.restaurant.service.SetmealService;
import com.restaurant.vo.DishItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealDishService setmealDishService;

    @Override
    public Long getUpdateUserID(String name) {

        Long updateUserID = setmealMapper.getUpdateUserID(name);
        return updateUserID;
    }

    /**
     * @param status
     * @param id
     * @return
     */
    @Override
    public Void noUse(Integer status, Long id) {

        setmealMapper.updateStatusById(status,id);

        return null;
    }

    /**
     * 通过名字查
     * @param name
     * @return
     */
    @Override
    public String getByName(String name) {
        Dish dish = setmealMapper.getByUsername(name);
        if (dish == null)
            return null;
        return dish.getName();
    }

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public Void saveWithDish(SetmealDTO setmealDto) {

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();


        setmealDishes.stream().map((add) -> {
            add.setSetmealId(setmealDto.getId());
            return add;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

        return null;
    }

    /**
     * 根据id查
     * @param ids
     * @return
     */
    @Override
    public SetmealDTO getByIdWithDish(Long ids) {
        Setmeal setmeal = this.getById(ids);
        SetmealDTO setmealDto = new SetmealDTO();

        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,ids);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }

    /**
     * 修改套餐信息
     * @param setmealDto
     */
    @Override
    public void updateWithDish(SetmealDTO setmealDto) {
        if (setmealDto.getStatus()==1){
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE_CH);
        }
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        List<Long> list = new ArrayList<>();
        list.add(setmealDto.getId());
        queryWrapper.in(Setmeal::getId,list);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);

        if(count > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        this.removeByIds(list);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,list);

        setmealDishService.remove(lambdaQueryWrapper);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto,setmeal);
        setmeal.setUpdateUser(BaseContext.getCurrentId());
        setmeal.setUpdateTime(LocalDateTime.now());
        this.save(setmeal);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除
     * @param list
     */
    @Override
    @Transactional
    public void deleteWithDish(List<Long> list) {
        //查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,list);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出业务异常
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }

        this.removeByIds(list);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,list);

        setmealDishService.remove(lambdaQueryWrapper);
    }


    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
