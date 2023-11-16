package com.restaurant.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.constant.MessageConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.SupplyDTO;
import com.restaurant.dto.SupplyPageQueryDTO;
import com.restaurant.entity.Supply;
import com.restaurant.exception.BusinessException;
import com.restaurant.result.Result;
import com.restaurant.service.SupplyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/admin/supply")
public class SupplyController {
    @Resource
    private SupplyService supplyService;
    @GetMapping ("/conditionQuery")
    @ApiOperation("条件查询")
    public Result<Page> queryByPageTable(SupplyPageQueryDTO supplyPageQueryDTO){

        int page = supplyPageQueryDTO.getPage();
        int pageSize = supplyPageQueryDTO.getPageSize();
        Page<Supply> infoPage = new Page<>(page, pageSize);
        QueryWrapper<Supply> queryWrapper = supplyService.getQueryWrapper(supplyPageQueryDTO);
        queryWrapper.orderByDesc("update_time");
        supplyService.page(infoPage,queryWrapper);
        return Result.success(infoPage);
    }

    /**
     * 删除供应商
     * @param supplyDTO
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("删除供应商信息")
    public Result deleteTable(@RequestBody SupplyDTO supplyDTO){
        if (supplyDTO == null || supplyDTO.getId() <=0){
            throw new BusinessException("参数错误");
        }
        Long id = supplyDTO.getId();
        //判断是否存在
        Supply oldSupply = supplyService.getById(id);
        if (oldSupply == null){
            throw new BusinessException("不存在");
        }
        boolean b = supplyService.removeById(id);
        return Result.success(b);
    }
    /**
     * 新增餐桌
     * @param supplyDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增供应商信息")
    public Result save(@RequestBody SupplyDTO supplyDTO){
        if (supplyDTO == null){
            throw new BusinessException("请求参数有误");
        }
        Supply supply = new Supply();
        BeanUtils.copyProperties(supplyDTO,supply);
        Long empId = BaseContext.getCurrentId();
        supply.setEmployeeId(empId);
        supplyService.validSupply(supply,true);
        supplyService.save(supply);
        Long id = supply.getId();
        return Result.success(id + MessageConstant.ADD_SUC);
    }

    /**
     * 更新供应商信息
     * @param supplyDTO
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("更新供应商信息")
    public Result updateTable(@RequestBody SupplyDTO supplyDTO){
        if (supplyDTO == null || supplyDTO.getId() <= 0){
            throw new BusinessException("参数异常");
        }
        Long id = supplyDTO.getId();
        Supply oldSupply = supplyService.getById(id);
        if (oldSupply == null){
            throw new BusinessException("不存在该供应商");
        }
        Supply supply = new Supply();
        BeanUtils.copyProperties(supplyDTO,supply);
        Long empId = BaseContext.getCurrentId();
        supply.setEmployeeId(empId);
        //校验是否存在此餐桌
        //参数校验
        supplyService.validSupply(supply,true);
        boolean result = supplyService.updateById(supply);
        return Result.success(result);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id供应商")
    public Result<SupplyDTO> get(@PathVariable Long id) {
        Supply supply = supplyService.getById(id);
        SupplyDTO supplyDTO = new SupplyDTO();
        BeanUtils.copyProperties(supply,supplyDTO);
        return Result.success(supplyDTO);
    }
    /**
     * 根据id删除餐桌
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation("根据id删除供应商")
    public Result deleteTableById(@PathVariable Long id){
        if (id == null){
            throw new BusinessException("id为空");
        }
        boolean b = supplyService.removeById(id);
        return Result.success(b);
    }
}
