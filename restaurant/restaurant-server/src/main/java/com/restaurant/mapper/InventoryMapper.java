package com.restaurant.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.restaurant.entity.Inventory;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface InventoryMapper extends BaseMapper<Inventory> {

    @Select("select * from inventory where name = #{name}")
    Inventory getByInventoryName(String name);
    void insertInventory(List<Inventory> InventoryList);
    void updateInventory(Inventory inventory);

    List<Inventory> list(Inventory inventory);
    @Update("update dish set status = #{status} where id = #{id}")
    Integer updateById(Integer status,Long id);


}
