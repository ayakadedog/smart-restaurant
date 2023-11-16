package com.restaurant.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.TablePageQueryDTO;
import com.restaurant.entity.CaterTable;

/**
* @author 小fan
* @description 针对表【table(桌号)】的数据库操作Service
* @createDate 2023-11-03 22:47:31
*/
public interface TableService extends IService<CaterTable> {
    /**
     * 校验
     * @param caterTable
     * @param add
     */
    void validTable(CaterTable caterTable, boolean add);
    /**
     * 获取查询条件
     *
     * @param tablePageQueryDTO
     * @return
     */
    QueryWrapper<CaterTable> getQueryWrapper(TablePageQueryDTO tablePageQueryDTO);
//    PageResult conditionSearch(TablePageQueryDTO tablePageQueryDTO);

//    public TableDTO getByIdWithTable(Long ids)
}
