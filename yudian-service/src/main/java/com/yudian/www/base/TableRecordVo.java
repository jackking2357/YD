package com.yudian.www.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格记录通用返回对象
 *

 * @since 2021-03-01
 */
@Data
@ApiModel(value = "表格记录对象", description = "表格记录通用返回")
public class TableRecordVo<T> {

    private List<T> records;

    private Long total;

    private Long pages;

    private Object other;

    public static TableRecordVo defaultValue() {
        TableRecordVo tableRecordVo = new TableRecordVo();
        tableRecordVo.setRecords(new ArrayList());
        tableRecordVo.setTotal(0L);
        tableRecordVo.setPages(0L);
        tableRecordVo.setOther(null);
        return tableRecordVo;
    }
}