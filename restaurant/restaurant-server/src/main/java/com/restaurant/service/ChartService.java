package com.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.ChartDTO;
import com.restaurant.entity.Chart;

import java.io.IOException;

public interface ChartService extends IService<Chart> {
    void getChat(ChartDTO chartDTO) throws IOException;

}
