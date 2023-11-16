package com.restaurant.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.restaurant.constant.MessageConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.TableDTO;
import com.restaurant.dto.TablePageQueryDTO;
import com.restaurant.entity.CaterTable;
import com.restaurant.exception.BusinessException;
import com.restaurant.result.Result;
import com.restaurant.service.TableService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/table")
public class TableController {

    @Resource
    private TableService tableService;

    /**
     * 分页查询
     */
    @GetMapping("/conditionQuery")
    public Result<Page> getTablePage(TablePageQueryDTO tablePageDTO){
        Integer page = tablePageDTO.getPage();
        Integer pageSize = tablePageDTO.getPageSize();
        Page<CaterTable> pageInfo = new Page<>(page, pageSize);
        QueryWrapper<CaterTable> queryWrapper = tableService.getQueryWrapper(tablePageDTO);
        queryWrapper.orderByDesc("update_time");
        tableService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询")
    public Result<TableDTO> get(@PathVariable Long id) {
        CaterTable table = tableService.getById(id);
        TableDTO tableDTO = new TableDTO();
        BeanUtils.copyProperties(table,tableDTO);
        return Result.success(tableDTO);
    }
//    @DeleteMapping
//    @ApiOperation("删除")
//    public Result deleteByIds(@RequestParam("ids") List<Long> ids){
//
//        setmealService.deleteWithDish(ids);
//        return Result.success(MessageConstant.DISH_DEL_SUC);
//    }
    /**
     * 根据id删除餐桌
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation("根据id删除菜桌")
    public Result deleteTableById(@PathVariable Long id){
        if (id == null){
            throw new BusinessException("id为空");
        }
        boolean b = tableService.removeById(id);
        return Result.success(b);
    }
    /**
     * 删除餐桌
     * @param tableDTO
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("删除菜桌")
    public Result deleteTable(@RequestBody TableDTO tableDTO){
        if (tableDTO == null || tableDTO.getId() <=0){
            throw new BusinessException("餐桌不存在");
        }
        Long id = tableDTO.getId();
        //判断是否存在
        CaterTable oldTable = tableService.getById(id);
        if (oldTable == null){
            throw new BusinessException("不存在");
        }
        boolean b = tableService.removeById(id);
        return Result.success(b);
    }
    /**
     * 新增餐桌
     * @param tableDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增餐桌")
    public Result save(@RequestBody TableDTO tableDTO){
        if (tableDTO == null){
            throw new BusinessException("请求参数有误");
        }
        CaterTable table = new CaterTable();
        BeanUtils.copyProperties(tableDTO,table);
        Long empId = BaseContext.getCurrentId();
        table.setEmployeeId(empId);
        table.setCreateTime(LocalDateTime.now());
        table.setUpdateTime(LocalDateTime.now());
        tableService.validTable(table,true);
        tableService.save(table);
        Long id = table.getId();
        return Result.success(id + MessageConstant.ADD_SUC);
    }

    /**
     * 更新菜品
     * @param tableDTO
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("更新餐桌")
    public Result updateTable(@RequestBody TableDTO tableDTO){
        if (tableDTO == null || tableDTO.getId() <= 0){
            throw new BusinessException("参数异常");
        }
        CaterTable caterTable = new CaterTable();
        BeanUtils.copyProperties(tableDTO,caterTable);
        Long empId = BaseContext.getCurrentId();
        caterTable.setEmployeeId(empId);
        caterTable.setCreateTime(LocalDateTime.now());
        caterTable.setUpdateTime(LocalDateTime.now());

        //校验是否存在此餐桌
//     Long id = tableDTO.getId();
//     CaterTable table = tableService.getById(id);
//     if (table == null){
//         throw new BusinessException("此餐桌不存在");
//     }
        //参数校验
        tableService.validTable(caterTable,true);
        boolean result = tableService.updateById(caterTable);
        return Result.success(result);
    }
}
