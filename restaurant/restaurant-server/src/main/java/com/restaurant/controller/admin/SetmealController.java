package com.restaurant.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.constant.MessageConstant;
import com.restaurant.constant.SetmealMultiplierConstant;
import com.restaurant.context.BaseContext;

import com.restaurant.dto.SetmealDTO;
import com.restaurant.dto.SetmealPageQueryDTO;
import com.restaurant.entity.Category;

import com.restaurant.entity.Employee;
import com.restaurant.entity.Setmeal;
import com.restaurant.entity.SetmealDish;
import com.restaurant.exception.BusinessException;
import com.restaurant.mapper.SetmealMapper;
import com.restaurant.result.Result;
import com.restaurant.service.CategoryService;
import com.restaurant.service.EmployeeService;
import com.restaurant.service.SetmealService;
import com.restaurant.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;
    @Resource
    private CategoryService categoryService;

    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private EmployeeService employeeService;
//    /**
//     * 分页查询
//     * @param setmealPageQueryDTO
//     * @return
//     */
//    @GetMapping("/page")
//    @ApiOperation("分页查询")
//    public Result<Page> page(SetmealPageQueryDTO  setmealPageQueryDTO ){
//
//        String name = setmealPageQueryDTO.getName();
//        Integer page = setmealPageQueryDTO.getPage();
//        Integer pageSize = setmealPageQueryDTO.getPageSize();
//        Integer status = setmealPageQueryDTO.getStatus();
//        Long categoryQueryById = setmealPageQueryDTO.getCategoryId();
//
//        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
//        Page<SetmealVO> voDtoPage = new Page<>();
//
//        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
//
//        queryWrapper.like(name != null, Setmeal::getName, name);
//
//        queryWrapper.eq(Objects.nonNull(status),Setmeal::getStatus, status )
//                .eq(Objects.nonNull(categoryQueryById),Setmeal::getCategoryId, categoryQueryById)
//                .orderByDesc(Setmeal::getUpdateTime);
//
//        setmealService.page(pageInfo, queryWrapper);
//
//        BeanUtils.copyProperties(pageInfo, voDtoPage, "records");
//        List<Setmeal> records = pageInfo.getRecords();
//
//        List<SetmealVO> list = records.stream().map((add) -> {
//
//            SetmealVO dishVO = new SetmealVO();
//
//            Long updateUserID = setmealService.getUpdateUserID(add.getName());
//            String updateUser = employeeService.getUsername(updateUserID);
//            BeanUtils.copyProperties(add, dishVO);
//            dishVO.setUpdateUser(updateUser);
//
//            Long categoryId = add.getCategoryId();
//            Category category = categoryService.getById(categoryId);
//
//            if (category != null) {
//                String categoryName = category.getName();
//                dishVO.setCategoryName(categoryName);
//            }
//            return dishVO;
//        }).collect(Collectors.toList());
//
//        voDtoPage.setRecords(list);
//        return Result.success(voDtoPage);
//    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    //todo todo
    @GetMapping("/page/use")
    @ApiOperation("分页查询")
    public Result<Page> page(SetmealPageQueryDTO  setmealPageQueryDTO ){

        String name = setmealPageQueryDTO.getName();
        Integer page = setmealPageQueryDTO.getPage();
        Integer pageSize = setmealPageQueryDTO.getPageSize();
        Integer status = setmealPageQueryDTO.getStatus();
        Long categoryQueryById = setmealPageQueryDTO.getCategoryId();

        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealVO> voDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Setmeal::getName, name);

        queryWrapper.eq(Setmeal::getStatus, 1 )
                .eq(Objects.nonNull(categoryQueryById),Setmeal::getCategoryId, categoryQueryById)
                .ne(Setmeal::getCategoryId, 1L)
                .orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, voDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealVO> list = records.stream().map((add) -> {

            SetmealVO dishVO = new SetmealVO();

            Long updateUserID = setmealService.getUpdateUserID(add.getName());
            String updateUser = employeeService.getUsername(updateUserID);
            BeanUtils.copyProperties(add, dishVO);
            dishVO.setUpdateUser(updateUser);

            Long categoryId = add.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishVO.setCategoryName(categoryName);
            }
            return dishVO;
        }).collect(Collectors.toList());

        voDtoPage.setRecords(list);
        return Result.success(voDtoPage);
    }


    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page/noUse")
    @ApiOperation("分页查询")
    //todo todo
    public Result<Page> pageUse(SetmealPageQueryDTO  setmealPageQueryDTO ){

        String name = setmealPageQueryDTO.getName();
        Integer page = setmealPageQueryDTO.getPage();
        Integer pageSize = setmealPageQueryDTO.getPageSize();
        Integer status = setmealPageQueryDTO.getStatus();
        Long categoryQueryById = setmealPageQueryDTO.getCategoryId();

        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealVO> voDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Setmeal::getName, name);

        queryWrapper.eq(Setmeal::getStatus, 0 )
                .eq(Objects.nonNull(categoryQueryById),Setmeal::getCategoryId, categoryQueryById)
                .ne(Setmeal::getCategoryId, 1L)
                .orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, voDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealVO> list = records.stream().map((add) -> {

            SetmealVO dishVO = new SetmealVO();

            Long updateUserID = setmealService.getUpdateUserID(add.getName());
            String updateUser = employeeService.getUsername(updateUserID);
            BeanUtils.copyProperties(add, dishVO);
            dishVO.setUpdateUser(updateUser);

            Long categoryId = add.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishVO.setCategoryName(categoryName);
            }
            return dishVO;
        }).collect(Collectors.toList());

        voDtoPage.setRecords(list);
        return Result.success(voDtoPage);
    }


    @GetMapping("/page/must")
    @ApiOperation("分页查询")
    //todo todo
    public Result<Page> pageMust(SetmealPageQueryDTO  setmealPageQueryDTO ){

        String name = setmealPageQueryDTO.getName();
        Integer page = setmealPageQueryDTO.getPage();
        Integer pageSize = setmealPageQueryDTO.getPageSize();
        Integer status = setmealPageQueryDTO.getStatus();
        Long categoryQueryById = setmealPageQueryDTO.getCategoryId();

        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealVO> voDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Setmeal::getName, name);

        queryWrapper.eq(Objects.nonNull(status),Setmeal::getStatus, status )
                .eq(Objects.nonNull(categoryQueryById),Setmeal::getCategoryId, categoryQueryById)
                .eq(Setmeal::getCategoryId,1L)
                .orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, voDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealVO> list = records.stream().map((add) -> {

            SetmealVO dishVO = new SetmealVO();

            Long updateUserID = setmealService.getUpdateUserID(add.getName());
            String updateUser = employeeService.getUsername(updateUserID);
            BeanUtils.copyProperties(add, dishVO);
            dishVO.setUpdateUser(updateUser);

            Long categoryId = add.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishVO.setCategoryName(categoryName);
            }
            return dishVO;
        }).collect(Collectors.toList());

        voDtoPage.setRecords(list);
        return Result.success(voDtoPage);
    }
    /**
     * 启禁用套餐
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启禁用套餐")
    public Result noUse(@PathVariable Integer status, long id){
        //todo 已经修改
        Integer integer = setmealMapper.selectDishState(id);
        if (integer == 0){
            return Result.error("套餐下菜品未起售");
        }
        setmealService.noUse(status, id);

        return Result.success();
    }



//    /**
//     * 批量禁用套餐
//     * @param status
//     * @param id
//     * @return
//     */
//    @PostMapping("/status/{status}")
//    public Result<String> setStatusByStatus(
//            @PathVariable Integer status,
//            @RequestParam("id") List<Long> ids) {
//        for (int i=0;i<id.size();i++){
//            setmealService.noUse(status, ids.get(i));
//        }
//        return Result.success("套餐禁用成功");
//    }




//    /**
//     * 启禁用套餐
//     * @param status
//     * @param id
//     * @return
//     */
//    @PostMapping("/status/{status}")
//    @ApiOperation("启禁用套餐")
//    public Result noUse(@RequestParam("ids") List<Long> ids,@PathVariable Integer status){
//
//        for(int i =0;i<ids.size();i++) {
//            setmealService.noUse(status, ids.get(i));
//        }
//        return Result.success();
//    }

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
//    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDto.categoryId")
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDto){

        String name = setmealService.getByName(setmealDto.getName());

        if(name != null){
            return Result.error(name + MessageConstant.ALREADY_EXISTS);
        }
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto,setmeal);

        Long empId = BaseContext.getCurrentId();
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setCreateUser(empId);
        setmeal.setUpdateUser(empId);

        //计算 套餐价格属于0.7倍到2倍的菜品单价到两倍之间

        BigDecimal setmealPrice = setmealDto.getPrice();
//        BigDecimal count = BigDecimal.valueOf(0);
//        for (int i=0;i<setmealDto.getSetmealDishes().size();i++){
//            count = count.add(setmealDto.getSetmealDishes().get(i).getPrice());
//        }
        BigDecimal count = setmealDto.getSetmealDishes().stream()
                .map(SetmealDish::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal lowerBound1 = count.multiply(SetmealMultiplierConstant.MINIMUM_MAGNIFICATION);  // 计算下限
        BigDecimal upperBound1 = count.multiply(SetmealMultiplierConstant.MAXIMUM_MAGNIFICATION);  // 计算上限

        if (setmealPrice.compareTo(lowerBound1) < 0 || setmealPrice.compareTo(upperBound1) > 0) {
            return Result.error("套餐价格应该在" + lowerBound1 + "到" + upperBound1 + "之间");
        }

        setmeal.setStatus(1);
        setmealService.save(setmeal);
        setmealDto.setId(setmeal.getId());
        setmealService.saveWithDish(setmealDto);
//
//        //计算 套餐价格属于0.7倍的菜品单价到两倍之间
//        BigDecimal setmealPrice = setmealDto.getPrice();
//        BigDecimal countBy = setmealService.getDishCountBySetmealID(setmealDto.getId());
//
//        BigDecimal lowerBound = countBy.multiply(new BigDecimal("0.7"));  // 计算下限
//        BigDecimal upperBound = countBy.multiply(new BigDecimal("2"));  // 计算上限
//
//        if (setmealPrice.compareTo(lowerBound) < 0 || setmealPrice.compareTo(upperBound) > 0) {
//            setmealService.removeById(setmealDto.getId());
////            setmealService.deleteWithSetmealID(setmealDto.getId());
//            return Result.error("套餐价格应该在" + lowerBound + "到" + upperBound + "之间");
//        }

        return Result.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询")
    public Result<SetmealDTO> get(@PathVariable Long id) {

        SetmealDTO setmealDto = setmealService.getByIdWithDish(id);

        return Result.success(setmealDto);
    }

    /**
     * 修改套餐
     * @param setmealDto
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    //    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDto){

        if (setmealDto.getStatus()==1){
            return Result.error(MessageConstant.SETMEAL_ON_SALE);
        }

        setmealService.updateWithDish(setmealDto);

        return Result.success(MessageConstant.EDIT_SUCCESS);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
//    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    @DeleteMapping
    @ApiOperation("删除")
    public Result deleteByIds(@RequestParam("ids") List<Long> ids){

        setmealService.deleteWithDish(ids);
        return Result.success(MessageConstant.SETMEAL_ON_SALE);
    }


}
