package com.restaurant.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.InventoryDTO;
import com.restaurant.dto.ShoppingCartDTO;
import com.restaurant.entity.Inventory;

import java.util.List;

public interface InventoryService extends IService<Inventory> {
    void addInventory(ShoppingCartDTO shoppingCartDTO);


    List<Inventory> showInventory();


    String getByName(String name);

    InventoryDTO getById(Long id);
    List<Inventory> list(Long categoryId);

    void update(InventoryDTO inventoryDTO);
    List<InventoryDTO> delete(List<Long> ids);
}
