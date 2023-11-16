package com.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.dto.SupplyPageQueryDTO;
import com.restaurant.entity.Supply;
import com.restaurant.exception.BusinessException;
import com.restaurant.mapper.SupplyMapper;
import com.restaurant.service.SupplyService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
* @author 小fan
* @description 针对表【supply(供应商)】的数据库操作Service实现
* @createDate 2023-11-04 18:44:36
*/
@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply>
    implements SupplyService {

    @Override
    public void validSupply(Supply supply, boolean add) {
        if (supply == null ){
            throw new BusinessException("请求参数为空");
        }

//        Long id = supply.getId();
        String supplierName = supply.getSupplierName();
        String contactName = supply.getContactName();
        String contactPhone = supply.getContactPhone();
        String email = supply.getEmail();
        String address = supply.getAddress();
        String remark = supply.getRemark();
//        Date startDate = supply.getStartDate();
//        Date endDate = supply.getEndDate();
        if (add) {
            if (StringUtils.isAnyBlank(supplierName, contactName, contactPhone, email, address, remark)) {
                throw new BusinessException("参数异常");
            }
        }
        if (StringUtils.isNotBlank(supplierName) && supplierName.length() > 20){
            throw new BusinessException("供应商名字过长");
        }
        if (StringUtils.isNotBlank(contactName) && contactName.length() > 20){
            throw new BusinessException("联系人名字过长");
        }
        if (StringUtils.isNotBlank(contactPhone) && contactPhone.length() != 11){
            throw new BusinessException("电话号不合法");
        }
        if (StringUtils.isNotBlank(email) && email.length() > 20){
            throw new BusinessException("邮箱过长");
        }
        if (StringUtils.isNotBlank(address) && address.length() > 40){
            throw new BusinessException("地址名过长");
        }
        if (StringUtils.isNotBlank(remark) && remark.length() > 500){
            throw new BusinessException("备注信息过多");
        }
    }
    @Override
    public QueryWrapper<Supply> getQueryWrapper(SupplyPageQueryDTO supplyPageQueryDTO) {
        QueryWrapper<Supply> queryWrapper = new QueryWrapper<>();
        if (supplyPageQueryDTO == null) {
            return queryWrapper;
        }
        Long id = supplyPageQueryDTO.getId();
        String supplierName = supplyPageQueryDTO.getSupplierName();
        String contactName = supplyPageQueryDTO.getContactName();
        String contactPhone = supplyPageQueryDTO.getContactPhone();
        String email = supplyPageQueryDTO.getEmail();
        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(supplierName), "table_number", supplierName);
        queryWrapper.like(StringUtils.isNotBlank(contactName), "seat_number", contactName);
        queryWrapper.like(StringUtils.isNotBlank(contactPhone), "area", contactPhone);
        queryWrapper.like(StringUtils.isNotBlank(email), "table_type", email);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),"id",id);
        return queryWrapper;
    }
}




