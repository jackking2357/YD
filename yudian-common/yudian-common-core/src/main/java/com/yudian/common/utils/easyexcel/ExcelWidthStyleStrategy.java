package com.yudian.common.utils.easyexcel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;


public class ExcelWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {


    private static int count = 0;

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head,
                                  Integer relativeRowIndex, Boolean isHead) {

        Sheet sheet = writeSheetHolder.getSheet();
        sheet.setColumnWidth(cell.getColumnIndex(), 5000);
    }
}


