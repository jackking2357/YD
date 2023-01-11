package com.yudian.common.utils.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class EasyExcelUtils {

    private static String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, encoding);
            return encoded.replace("\\+", "%20").replace("*", "%2A")
                    .replace("~", "%7E").replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    
    public static <T> void webExport(HttpServletResponse response, String fileName, List<EasyExcelEntity<T>> tList) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + urlEncode(fileName, "UTF-8") + ".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

        for (EasyExcelEntity<T> tEasyExcelEntity : tList) {
            WriteSheet writeSheet = EasyExcel.writerSheet(null, tEasyExcelEntity.getSheetName())
                    .head(tEasyExcelEntity.getClazz())

                    .registerWriteHandler(new FirstHeaderFreeze())
                    .registerWriteHandler(new ColumnWidthStyleStrategy())
                    .registerWriteHandler(new TextCellWriteHandlerImpl())
                    .build();
            if (tEasyExcelEntity.getCustomHeaderMode()) {
                writeSheet = EasyExcel.writerSheet(null, tEasyExcelEntity.getSheetName())
                        .head(tEasyExcelEntity.getCustomHeaderList())
                        .registerWriteHandler(new FirstHeaderFreeze())
                        .registerWriteHandler(new ColumnWidthStyleStrategy())
                        .registerWriteHandler(new TextCellWriteHandlerImpl())
                        .build();
            }
            excelWriter.write(tEasyExcelEntity.getData(), writeSheet);
        }
        excelWriter.finish();
    }

    
    public static <T> void webExportTemplate(HttpServletResponse response, String fileName, List<EasyExcelEntity<T>> tList) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + urlEncode(fileName, "UTF-8") + ".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

        for (EasyExcelEntity<T> tEasyExcelEntity : tList) {
            WriteSheet writeSheet = EasyExcel.writerSheet(null, tEasyExcelEntity.getSheetName())
                    .head(tEasyExcelEntity.getClazz())
                    .registerWriteHandler(new FirstHeaderFreeze())
                    .registerWriteHandler(new TextCellWriteHandlerImpl())
                    .build();
            if (tEasyExcelEntity.getCustomHeaderMode()) {
                writeSheet = EasyExcel.writerSheet(null, tEasyExcelEntity.getSheetName())
                        .head(tEasyExcelEntity.getCustomHeaderList())
                        .registerWriteHandler(new FirstHeaderFreeze())
                        .registerWriteHandler(new ColumnWidthStyleStrategy())
                        .registerWriteHandler(new TextCellWriteHandlerImpl())
                        .build();
            }
            excelWriter.write(tEasyExcelEntity.getData(), writeSheet);
        }
        excelWriter.finish();
    }
}
