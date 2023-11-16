package com.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.config.AiManager;
import com.restaurant.dto.ChartDTO;
import com.restaurant.entity.Chart;
import com.restaurant.entity.SetmealDish;
import com.restaurant.mapper.ChartMapper;
import com.restaurant.mapper.SetmealDishMapper;
import com.restaurant.service.ChartService;
import com.restaurant.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart> implements ChartService {


    @Resource
    private ChartMapper chartMapper;

    @Resource
    private AiManager aiManager;
    @Override
    public void getChat(ChartDTO chartDTO) throws IOException {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(chartDTO.getGoal());
        stringBuffer.append("\n");
        stringBuffer.append(chartDTO.getDescription());
        stringBuffer.append("\n");
        stringBuffer.append(chartDTO.getReason());

        Long modelId = 1720791806457131009L;

        System.out.println(stringBuffer.toString());

        //String result = "aiManager.doChat(modelId,stringBuffer.toString())";

        String result = aiManager.doChat(modelId,stringBuffer.toString());
        Chart chart = new Chart();

        chart.setGoal(chartDTO.getGoal());
        chart.setReason(stringBuffer.toString());
        chart.setDescription(result);
        chart.setCreateTime(LocalDateTime.now());

        chartMapper.insert(chart);
    }

}

