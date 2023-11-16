package com.restaurant.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.constant.MessageConstant;
import com.restaurant.constant.StatusConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.CategoryDTO;
import com.restaurant.dto.CategoryPageQueryDTO;
import com.restaurant.entity.Category;
import com.restaurant.result.Result;
import com.restaurant.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

//    /**
//     * 分页查询
//     * @param categoryPageQueryDTO
//     * @return
//     */
//    @GetMapping("/page")
//    @ApiOperation("分页查询")
//    public Result<Page> page(CategoryPageQueryDTO categoryPageQueryDTO ){
//
//        String name = categoryPageQueryDTO.getName();
//        Integer page = categoryPageQueryDTO.getPage();
//        Integer pageSize = categoryPageQueryDTO.getPageSize();
//        Integer type = categoryPageQueryDTO.getType();
//        Page<Category> pageInfo = new Page(page,pageSize);
//
//        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
//        queryWrapper.like(StringUtils.isNotEmpty(name), Category::getName, name);
//        queryWrapper.eq(Objects.nonNull(type),Category::getType, type );
//        queryWrapper.orderByAsc(Category::getSort);
//
//        categoryService.page(pageInfo, queryWrapper);
//
//        return Result.success(pageInfo);
//    }

    /**
     * 分页查询启用的分类
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page/use")
    @ApiOperation("分页查询启用的分类")
    public Result<Page<Category>> pageActiveCategories(CategoryPageQueryDTO categoryPageQueryDTO) {
        return pageCategoriesByStatus(categoryPageQueryDTO, 1); // 假设状态1表示分类启用
    }

    /**
     * 分页查询停用的分类
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page/noUse")
    @ApiOperation("分页查询停用的分类")
    public Result<Page<Category>> pageInactiveCategories(CategoryPageQueryDTO categoryPageQueryDTO) {
        return pageCategoriesByStatus(categoryPageQueryDTO, 0); // 假设状态0表示分类停用
    }

    /**
     * 根据状态分页查询分类的通用方法
     * @param categoryPageQueryDTO
     * @param status
     * @return
     */
    private Result<Page<Category>> pageCategoriesByStatus(CategoryPageQueryDTO categoryPageQueryDTO, Integer status) {
        String name = categoryPageQueryDTO.getName();
        Integer page = categoryPageQueryDTO.getPage();
        Integer pageSize = categoryPageQueryDTO.getPageSize();
        Integer type = categoryPageQueryDTO.getType();

        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Category::getName, name);
        queryWrapper.eq(Objects.nonNull(type), Category::getType, type);
        queryWrapper.eq(Category::getStatus, status); // 过滤状态
        queryWrapper.ne(Category::getType,3);
        queryWrapper.ne(Category::getType,4);
        queryWrapper.ne(Category::getId,2L);
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }



    /**
     * 条件查询  目测没个卵用
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("条件查询")
    public Result<List<Category>> list(Integer type) {

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( Category::getType, type );
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return Result.success(list);
    }

    /**
     * 禁用员工账号
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result noUse(@PathVariable Integer status, long id){

        categoryService.noUse(status, id);

        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteCategory(Long id){

        categoryService.remove(id);
        return Result.success();

    }

    /**
     * 编辑分类信息
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑分类信息")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //TODO 记得全局替换,自动装配变量
        Long empId = BaseContext.getCurrentId();
        category.setUpdateUser(empId);
        category.setUpdateTime(LocalDateTime.now());

        categoryService.updateById(category);
        return Result.success();
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO){

        String name = categoryService.getByName(categoryDTO.getName());
        if(name != null){
            return Result.error( name + MessageConstant.ALREADY_EXISTS);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //TODO 记得全局替换,自动装配变量
        Long empId = BaseContext.getCurrentId();
        category.setUpdateUser(empId);
        category.setUpdateTime(LocalDateTime.now());
        category.setStatus(StatusConstant.ENABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(empId);
        categoryService.save(category);
        return Result.success();
    }
}
