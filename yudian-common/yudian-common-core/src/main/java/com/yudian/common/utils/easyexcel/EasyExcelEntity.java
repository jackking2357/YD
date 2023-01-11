package com.yudian.common.utils.easyexcel;

import lombok.Data;

import java.util.List;

@Data
public class EasyExcelEntity<T> {

    private String sheetName;

    private Class clazz;

    private List<T> data;

    
    private Boolean customHeaderMode = false;

    
    private List<List<String>> customHeaderList;
}
