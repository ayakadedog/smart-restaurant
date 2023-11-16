package com.restaurant.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.restaurant.constant.MessageConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.InventoryDTO;
import com.restaurant.dto.InventoryPageQueryDTO;
import com.restaurant.entity.Category;
import com.restaurant.entity.Inventory;
import com.restaurant.result.Result;
import com.restaurant.service.CategoryService;
import com.restaurant.service.InventoryService;
import com.restaurant.vo.InventoryVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/inventory")
public class InventoryController {
    @Resource
    private InventoryService inventoryService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private RedisTemplate redisTemplate;


    @GetMapping("/list")
    @ApiOperation("根据分类id查询分类")
    public Result<List<Inventory>> list(Long categoryId){
        List<Inventory> list = inventoryService.list(categoryId);
        return Result.success(list);
    }

    @PostMapping("/add")
    @ApiOperation("新增库存")
    public Result save(@RequestBody InventoryDTO inventoryDTO){

        String name = inventoryService.getByName(inventoryDTO.getName());
        if(name != null){
            return Result.error(MessageConstant.ALREADY_EXISTS);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime localDateTime = LocalDateTime.parse(inventoryDTO.getPurchaseTime(), formatter);
        LocalDate localDate = localDateTime.toLocalDate();

        int daysToAdd = Integer.parseInt(inventoryDTO.getExpirationTime());

        // 使用采购日期加上过期天数来计算过期日期
        inventoryDTO.setExpirationDate(localDate.plusDays(daysToAdd));

        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(inventoryDTO,inventory);


        Long empId = BaseContext.getCurrentId();
        inventory.setPurchaseDate(localDate);
        inventory.setCreatedTime(LocalDateTime.now());
        inventory.setCreatedUser(empId);
        inventory.setExpirationDate(inventoryDTO.getExpirationDate());
        inventory.setModifiedTime(LocalDateTime.now());
        inventory.setModifiedUser(empId);
        inventory.setImage(inventoryDTO.getImage());
        inventoryService.save(inventory);

//        inventoryService.saveWithFlavor(dishDTO,dish.getId());
        //TODO
        //cleanCache("dish_" + dishDTO.getCategoryId());


        return Result.success(inventoryDTO.getName() + MessageConstant.ADD_SUC);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询库存信息")
    public Result<InventoryDTO> get(@PathVariable Long id){

        InventoryDTO inventoryDto = inventoryService.getById(id);

        return Result.success(inventoryDto);
    }

    @PostMapping("update")
    @ApiOperation("更新库存信息")
    public Result update(@RequestBody InventoryDTO inventoryDTO){

        System.out.println(inventoryDTO.toString());
        //TODO
        //cleanCache("dish_*");

        return Result.success(MessageConstant.EDIT_SUCCESS);
    }

    @DeleteMapping
    @ApiOperation("根据id删除")
    public Result deleteByIds(@RequestParam("ids") List<Long> ids){

        inventoryService.delete(ids);


        return Result.success(MessageConstant.DISH_DEL_SUC);
    }
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<Page> page(InventoryPageQueryDTO inventoryPageQueryDTO){

        String name = inventoryPageQueryDTO.getName();
        Integer page = inventoryPageQueryDTO.getPage();
        Integer pageSize = inventoryPageQueryDTO.getPageSize();
        Integer categoryQueryById = inventoryPageQueryDTO.getCategoryId();

        Page<Inventory> pageInfo = new Page<>(page, pageSize);
        Page<InventoryVO> voDtoPage = new Page<>();

        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Inventory::getName, name);
        queryWrapper.eq(Objects.nonNull(categoryQueryById),Inventory::getCategoryId, categoryQueryById );
        queryWrapper.orderByDesc(Inventory::getModifiedTime);

        inventoryService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, voDtoPage, "records");
        List<Inventory> records = pageInfo.getRecords();

        List<InventoryVO> list = records.stream().map((add) -> {

            InventoryVO inventoryVO = new InventoryVO();

            BeanUtils.copyProperties(add, inventoryVO);

            Long categoryId = add.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                inventoryVO.setCategoryName(categoryName);
            }
            return inventoryVO;
        }).collect(Collectors.toList());

        voDtoPage.setRecords(list);
        return Result.success(voDtoPage);
    }
}
