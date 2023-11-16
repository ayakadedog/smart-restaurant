package com.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.InventoryDTO;
import com.restaurant.dto.ShoppingCartDTO;
import com.restaurant.entity.Inventory;
import com.restaurant.mapper.InventoryMapper;
import com.restaurant.service.InventoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Resource
    private InventoryMapper inventoryMapper;
    @Resource
    private InventoryService inventoryService;
    @Override
    public void addInventory(ShoppingCartDTO shoppingCartDTO) {

    }

    @Override
    public List<Inventory> showInventory() {
        return null;
    }

    @Override
    public String getByName(String name) {
        Inventory inventory = inventoryMapper.getByInventoryName(name);
        if (inventory == null)
            return null;
        return inventory.getName();
    }

    @Override
    @Transactional
    public InventoryDTO getById(Long id) {

        Inventory inventory = inventoryMapper.selectById(id);


        InventoryDTO inventoryDTO = new InventoryDTO();

        BeanUtils.copyProperties(inventory,inventoryDTO);

        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inventory::getId,inventory.getId());

        return inventoryDTO;
    }

    public List<Inventory> list(Long categoryId) {
        Inventory inventory=Inventory.builder()
                .categoryId(categoryId)
                .build();
        return inventoryMapper.list(inventory);
    }

    @Override
    @Transactional
    public void update(InventoryDTO inventoryDTO) {

        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(inventoryDTO, inventory);
        inventory.setModifiedUser(BaseContext.getCurrentId());
        inventory.setModifiedTime(LocalDateTime.now());
        this.updateById(inventory);

        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Inventory::getId, inventoryDTO.getId());

        inventoryService.remove(queryWrapper);
    }

    @Override
    @Transactional
    public List<InventoryDTO> delete(List<Long> list) {

        List<InventoryDTO> inventoryDTOList= list.stream().map((ids) -> {
            InventoryDTO inventoryDTO = new InventoryDTO();


            InventoryDTO byId = this.getById(ids);

            this.removeById(ids);

            BeanUtils.copyProperties(byId,inventoryDTO);

            LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Inventory::getId,ids);

            inventoryService.remove(queryWrapper);

            return inventoryDTO;
        }).collect(Collectors.toList());

        return inventoryDTOList;

    }
}
