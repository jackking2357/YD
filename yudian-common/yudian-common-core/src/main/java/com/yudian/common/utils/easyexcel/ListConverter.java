package com.yudian.common.utils.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListConverter implements Converter<List<String>> {

    @Override
    public Class<List> supportJavaTypeKey() {
        return List.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public List<String> convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                          GlobalConfiguration globalConfiguration) {
        return Arrays.asList(cellData.getStringValue().split("，"));
    }

    @Override
    public CellData<List<String>> convertToExcelData(List<String> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new CellData<>(value.stream().collect(Collectors.joining("，")));
    }

}
