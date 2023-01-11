package com.yudian.common.utils.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class DbMySqlEnvConverter implements Converter<Integer> {


    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if ("本地".equals(cellData.getStringValue())) {
            return 1;
        } else if ("测试".equals(cellData.getStringValue())) {
            return 2;
        } else if ("生产".equals(cellData.getStringValue())) {
            return 3;
        } else if ("演示".equals(cellData.getStringValue())) {
            return 4;
        }
        return -1;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (1 == value) {
            return new CellData("本地");
        }
        if (2 == value) {
            return new CellData("测试");
        }
        if (3 == value) {
            return new CellData("生产");
        }
        if (4 == value) {
            return new CellData("演示");
        }
        return new CellData("未找到枚举值");
    }
}