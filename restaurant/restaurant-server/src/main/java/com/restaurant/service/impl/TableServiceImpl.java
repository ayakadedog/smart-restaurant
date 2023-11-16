package com.restaurant.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.restaurant.dto.TablePageQueryDTO;
import com.restaurant.entity.CaterTable;
import com.restaurant.exception.BusinessException;
import com.restaurant.mapper.TableMapper;
import com.restaurant.service.TableService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 小fan
* @description 针对表【table(桌号)】的数据库操作Service实现
* @createDate 2023-11-03 22:47:31
*/
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, CaterTable> implements TableService {
@Resource
private TableMapper tableMapper;
    @Override
    public void validTable(CaterTable caterTable, boolean add) {
    if (caterTable == null){
        throw new BusinessException("请求参数为空");
}
        String tableNumber = caterTable.getTableNumber();
        String seatNumber = caterTable.getSeatNumber();
        String area = caterTable.getArea();
        String tableType = caterTable.getTableType();
        String remark = caterTable.getRemark();
    if (add){
    if (StringUtils.isAnyBlank(tableNumber,seatNumber,area,tableType,remark)){
        throw new BusinessException("修改参数异常");
    }
    }
        Long id = caterTable.getId();
        CaterTable table = this.getById(id);

        if (table == null ||!tableNumber.equals(table.getTableNumber())) {
        //餐桌号不能重复
        QueryWrapper<CaterTable> queryWrapper = new QueryWrapper();
        queryWrapper.eq("table_number", tableNumber);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException("桌号重复");
        }
    }
        if (StringUtils.isNotBlank(seatNumber) && seatNumber.length() > 20){
            throw new BusinessException("座位容纳数不合理");
        }
        if (StringUtils.isNotBlank(area) && area.length() >30 ){
            throw new BusinessException("参数异常");
        }
        if (StringUtils.isNotBlank(tableType) && tableType.length() > 30){
            throw new BusinessException("材料名字过长");
        }
        if (StringUtils.isNotBlank(remark) && remark.length() > 100 ){
         throw new BusinessException("备注过长");
        }
    }

    @Override
    public QueryWrapper<CaterTable> getQueryWrapper(TablePageQueryDTO tablePageQueryDTO) {
        QueryWrapper<CaterTable> queryWrapper = new QueryWrapper<>();
        if (tablePageQueryDTO == null) {
            return queryWrapper;
        }
        Long id = tablePageQueryDTO.getId();
        String tableNumber = tablePageQueryDTO.getTableNumber();
        String seatNumber = tablePageQueryDTO.getSeatNumber();
        String area = tablePageQueryDTO.getArea();
        String tableType = tablePageQueryDTO.getTableType();
        String remark = tablePageQueryDTO.getRemark();
        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(tableNumber), "table_number", tableNumber);
        queryWrapper.like(StringUtils.isNotBlank(seatNumber), "seat_number", seatNumber);
        queryWrapper.like(StringUtils.isNotBlank(area), "area", area);
        queryWrapper.like(StringUtils.isNotBlank(tableType), "table_type", tableType);
        queryWrapper.like(StringUtils.isNotBlank(remark), "remark", remark);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),"id",id);
        return queryWrapper;
    }

//    /**
//     * 根据id查
//     * @param ids
//     * @return
//     */
//    @Override
//    public TableDTO getByIdWithTable(Long ids) {
//        CaterTable oldTable = this.getById(ids);
//        TableDTO tableDTO = new TableDTO();
//
//        BeanUtils.copyProperties(oldTable,tableDTO);
//
//        LambdaQueryWrapper<CaterTable> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(CaterTable::getSetmealId,ids);
//        List<SetmealDish> list = setmealDishService.list(queryWrapper);
//        setmealDto.setSetmealDishes(list);
//
//        return tableDTO;
//    }
}




