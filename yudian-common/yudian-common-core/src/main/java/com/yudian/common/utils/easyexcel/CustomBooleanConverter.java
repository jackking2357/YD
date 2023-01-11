package com.yudian.common.utils.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class CustomBooleanConverter implements Converter<Boolean> {

    private static Set<String> yes = new HashSet<>();
    private static Set<String> not = new HashSet<>();

    static {
        yes.add("TRUE");
        yes.add("true");

        not.add("FALSE");
        not.add("false");
    }

    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return cellData.getBooleanValue();
    }

    @Override
    public CellData convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(value ? "TRUE" : "FALSE");
    }
}