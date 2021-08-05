package com.hw.tobcore.util;


import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtil {
    public static XSSFWorkbook getXSSFWorkbook(String sheetName, String[] title, String[][] values, XSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new XSSFWorkbook();
        }
        //第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        // 声明列对象
        XSSFCell cell = null;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.length-1));
        cell=row.createCell(0);
        cell.setCellValue(sheetName);
        cell.setCellStyle(style);
        // 创建标题
        row=sheet.createRow(1);
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        // 创建内容
        if (values!=null){
            for (int i = 0; i < values.length; i++) {
                row = sheet.createRow(i + 2);
                for (int j = 0; j < values[i].length; j++) {
                    // 将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values[i][j]);
                }

            }
        }
        return wb;
    }
}
