package com.restaurant.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurant.constant.MessageConstant;
import com.restaurant.dto.ChartDTO;
import com.restaurant.dto.DishDTO;
import com.restaurant.dto.EmployeePageQueryDTO;
import com.restaurant.entity.Chart;
import com.restaurant.entity.Employee;
import com.restaurant.result.Result;
import com.restaurant.service.ChartService;
import com.restaurant.utils.AliOssUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/chart")
public class ChartController {

    String reason;

    @Resource
    private ChartService chartService;
    @PostMapping("/csv")
    public Result<Void> chart(MultipartFile file) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        InputStream inputStream = file.getInputStream();
        XSSFWorkbook excel = new XSSFWorkbook(inputStream);

        //获取表格文件的Sheet页
        XSSFSheet sheet = excel.getSheet("Sheet1");

        //获得第4行
        XSSFRow row = sheet.getRow(6);
        String stringCellValue1 = row.getCell(1).getStringCellValue();
        String stringCellValue2 = row.getCell(2).getStringCellValue();
        String stringCellValue3 = row.getCell(3).getStringCellValue();
        String stringCellValue4 = row.getCell(4).getStringCellValue();
        String stringCellValue5 = row.getCell(5).getStringCellValue();
        String stringCellValue6 = row.getCell(6).getStringCellValue();

        String firstRowData = stringCellValue1 + " " +stringCellValue2+ " "  + stringCellValue3+ " "  + stringCellValue4+ " "  + stringCellValue5+ " "  + stringCellValue6 + "\n";
        stringBuffer.append(firstRowData);
        //填充明细数据
        for (int i = 0; i < 30; i++) {
            //获得某一行
            row = sheet.getRow(7 + i);
            String stringCellValue11 = row.getCell(1).getStringCellValue();
            String stringCellValue12 = row.getCell(2).getRawValue();
            String stringCellValue13 = row.getCell(3).getRawValue();
            String stringCellValue14 = row.getCell(4).getRawValue();
            String stringCellValue15 = row.getCell(5).getRawValue();
            String stringCellValue16 = row.getCell(6).getRawValue();

            String rowData = stringCellValue11+ " " + stringCellValue12+ " " + stringCellValue13+ " " + stringCellValue14+ " " + stringCellValue15+ " " + stringCellValue16 + "\n";
            stringBuffer.append(rowData);
        }

        String result = stringBuffer.toString();
        reason=result;
        return Result.success();
    }

    @PostMapping("/add")
    public Result<Void> save (@RequestBody ChartDTO chartDTO) throws IOException {

        chartDTO.setReason(reason);
        chartService.getChat(chartDTO);

        return Result.success();

    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<Page> page(EmployeePageQueryDTO employeePageQueryDTO ){

        String name = employeePageQueryDTO.getName();
        Integer page = employeePageQueryDTO.getPage();
        Integer pageSize = employeePageQueryDTO.getPageSize();

        Page<Chart> pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Chart> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), Chart::getGoal, name);
        queryWrapper.orderByDesc(Chart::getCreateTime);

        chartService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }


    /**
     * 根据id删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除")
    public Result deleteByIds(@RequestParam("ids") List<Long> ids){

        chartService.removeByIds(ids);

        return Result.success(MessageConstant.DISH_DEL_SUC);
    }
}
