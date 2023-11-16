package com.restaurant.service;

import com.restaurant.vo.BusinessDataVO;
import com.restaurant.vo.DishOverViewVO;
import com.restaurant.vo.OrderOverViewVO;
import com.restaurant.vo.SetmealOverViewVO;
import java.time.LocalDateTime;

public interface WorkspaceService {

    /**
     * 统计营业数据
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 订单管理
     * @return
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 菜品总览
     * @return
     */
    DishOverViewVO getDishOverView();

    /**
     * 查询套餐总览
     * @return
     */
    SetmealOverViewVO getSetmealOverView();


}
