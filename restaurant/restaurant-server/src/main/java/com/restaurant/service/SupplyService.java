package com.restaurant.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.restaurant.dto.SupplyPageQueryDTO;
import com.restaurant.entity.Supply;

/**
* @author 小fan
* @description 针对表【supply(供应商)】的数据库操作Service
* @createDate 2023-11-04 18:44:36
*/
public interface SupplyService extends IService<Supply> {
    /**
     * 校验参数
     * @param supply
     * @param b
     */
    void validSupply(Supply supply, boolean b);

    /**
     * 获取查询条件
     * @param supplyPageQueryDTO
     * @return
     */
    public QueryWrapper<Supply> getQueryWrapper(SupplyPageQueryDTO supplyPageQueryDTO);
}
