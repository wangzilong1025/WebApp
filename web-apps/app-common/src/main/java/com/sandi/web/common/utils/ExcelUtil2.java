/**
 * $Id: ExcelUtil.java,v 1.0 2015/9/15 20:59 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导出EXCEL工具
 *
 * @author zhangrp
 * @version $Id: ExcelUtil.java,v 1.1 2015/9/15 20:59 zhangrp Exp $
 *          Created on 2015/9/15 20:59
 */
public class ExcelUtil2 {

    /**
     * excel类型为 xls
     * excel版本为2003
     */
    public final static String XLS = "XLS";

    /**
     * excel类型为xlsx
     * excel版本为2007以后
     */
    public final static String XLSX = "XLSX";


    public static String getExcelType(String filePath) {
        if (isXLS(filePath)) {
            return XLS;
        } else if (isXLSX(filePath)) {
            return XLSX;
        }
        return XLSX;
    }

    public static boolean isXLS(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isXLSX(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }



    /**
     * 导出根据dataGird 导出excel
     *
     * @param excelType
     * @param sheetName
     * @param columns
     * @param formatDataMap
     * @param rows
     * @param outputstream
     * @throws Exception
     */
    public static void exportExcelForDataGrid(
            String excelType,
            String sheetName,
            List<Map<String, Object>> columns,
            Map<String, Map<String, String>> formatDataMap,
            List<Map<String, Object>> rows,
            OutputStream outputstream) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Workbook wb_w = null;
        if (excelType.equals("XLS")) {
            wb_w = new HSSFWorkbook();
        } else {
            wb_w = new XSSFWorkbook();
        }

        CellStyle cellStyle_head = wb_w.createCellStyle();
        cellStyle_head.setAlignment(CellStyle.ALIGN_CENTER);
        Font headerFont = wb_w.createFont(); // 字体
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setFontName("微软雅黑");
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle_head.setFont(headerFont);
        cellStyle_head.setBorderBottom((short) 1);
        cellStyle_head.setBorderLeft((short) 1);
        cellStyle_head.setBorderRight((short) 1);
        cellStyle_head.setBorderTop((short) 1);
        cellStyle_head.setWrapText(false);
        cellStyle_head.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle_head.setFillForegroundColor(HSSFColor.YELLOW.index);

        CellStyle cellStyle_content_0 = wb_w.createCellStyle();
        cellStyle_content_0.setAlignment(CellStyle.ALIGN_LEFT);
        headerFont = wb_w.createFont(); // 字体
        headerFont.setFontHeightInPoints((short) 9);
        headerFont.setFontName("微软雅黑");
        cellStyle_content_0.setFont(headerFont);
        cellStyle_content_0.setBorderBottom((short) 1);
        cellStyle_content_0.setBorderLeft((short) 1);
        cellStyle_content_0.setBorderRight((short) 1);
        cellStyle_content_0.setBorderTop((short) 1);
        cellStyle_content_0.setWrapText(false);
        cellStyle_content_0.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle_content_0.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);

        CellStyle cellStyle_content_1 = wb_w.createCellStyle();
        cellStyle_content_1.setAlignment(CellStyle.ALIGN_LEFT);
        headerFont = wb_w.createFont(); // 字体
        headerFont.setFontHeightInPoints((short) 9);
        headerFont.setFontName("微软雅黑");
        cellStyle_content_1.setFont(headerFont);
        cellStyle_content_1.setBorderBottom((short) 1);
        cellStyle_content_1.setBorderLeft((short) 1);
        cellStyle_content_1.setBorderRight((short) 1);
        cellStyle_content_1.setBorderTop((short) 1);
        cellStyle_content_1.setWrapText(false);

        Sheet sheet_w = wb_w.createSheet(sheetName);
        //设置列宽
        for (int p = 0; p < columns.size(); p++) {
            sheet_w.setColumnWidth(p, 30 * 200);
        }

        //设置第一行 行头 head
        Row row_w = sheet_w.createRow(0);
        for (int k = 0; k < columns.size(); k++) {
            Cell cell_w = row_w.createCell(k);
            cell_w.setCellStyle(cellStyle_head);

            if (columns.get(k).get("title") != null) {
                cell_w.setCellValue("" + columns.get(k).get("title"));
            } else if (columns.get(k).get("field") != null) {
                cell_w.setCellValue("" + columns.get(k).get("field"));
            } else {
                cell_w.setCellValue("");
            }
        }

        //设置表格内容
        for (int j = 0; j < rows.size(); j++) {
            row_w = sheet_w.createRow(j + 1);
            Map<String, Object> map = rows.get(j);
            for (int k = 0; k < columns.size(); k++) {
                Cell cell_w = row_w.createCell(k);
                if (j % 2 == 1) {
                    cell_w.setCellStyle(cellStyle_content_0);
                } else {
                    cell_w.setCellStyle(cellStyle_content_1);
                }
                if (columns.get(k).get("field") != null && map.get(columns.get(k).get("field")) != null) {
                    String field = columns.get(k).get("field").toString();
                    String value = "";
                    if (map.get(field) == null) {
                        value = "";
                    } else {
                        if (map.get(field) instanceof Date) {
                            value = formatter.format((Date) map.get(field));
                        } else {
                            value = "" + map.get(field);
                        }
                    }
                    if (formatDataMap.get(field) != null) {
                        if (formatDataMap.get(field).get(value) != null) {
                            cell_w.setCellValue(formatDataMap.get(field).get(value));
                        } else if (value.indexOf(",") >= 0) {
                            String values[] = value.split(",");
                            String str = "";
                            for (int i = 0; i < values.length; i++) {
                                if (formatDataMap.get(field).get(values[i]) != null) {
                                    str = str + formatDataMap.get(field).get(values[i]) + "，";
                                }
                            }
                            if (str.length() > 0) {
                                str = str.substring(0, str.length() - 1);
                            } else {
                                str = value;
                            }
                            cell_w.setCellValue(str);
                        } else {
                            cell_w.setCellValue(value);
                        }
                    } else {
                        cell_w.setCellValue(value);
                    }
                } else {
                    cell_w.setCellValue("");
                }
            }
        }
        try {
            wb_w.write(outputstream);
            outputstream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 导出excle仅包含一个sheet
     * @param excelType
     * @param sheetName
     * @param columns
     * @param rows
     * @param mergers
     * @param outputstream
     * @throws Exception
     */
    public static void exportExcel(
            String excelType,
            String sheetName,
            List<List<Object>> columns,
            List<List<Object>> rows,
            List<Merger> mergers,
            OutputStream outputstream) throws Exception {

        List<String> sheetNames_ = new ArrayList<String>();
        sheetNames_.add(sheetName);
        List<List<List<Object>>> columns_ =new ArrayList<List<List<Object>>>();
        columns_.add(columns);
        List<List<List<Object>>> rows_ = new ArrayList<List<List<Object>>>();
        rows_.add(rows);
        List<List<Merger>> mergers_ = new ArrayList<List<Merger>>();
        mergers_.add(mergers);

        exportExcelWithMoreSheet(excelType, sheetNames_, columns_, rows_, mergers_, outputstream);
    }



    /**
     * 导出多个sheet的excel
     * @param excelType
     * @param sheetNames
     * @param columns
     * @param rows
     * @param mergers
     * @param outputstream
     * @throws Exception
     */
    public static void exportExcelWithMoreSheet(
            String excelType,
            List<String> sheetNames,
            List<List<List<Object>>> columns,
            List<List<List<Object>>> rows,
            List<List<Merger>> mergers,
            OutputStream outputstream) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Workbook wb_w = null;
        if (excelType.equals("XLS")) {
            wb_w = new HSSFWorkbook();
        } else {
            wb_w = new XSSFWorkbook();
        }

        CellStyle cellStyle_head = wb_w.createCellStyle();
        cellStyle_head.setAlignment(CellStyle.ALIGN_CENTER);
        Font headerFont = wb_w.createFont(); // 字体
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setFontName("微软雅黑");
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle_head.setFont(headerFont);
        cellStyle_head.setBorderBottom((short) 1);
        cellStyle_head.setBorderLeft((short) 1);
        cellStyle_head.setBorderRight((short) 1);
        cellStyle_head.setBorderTop((short) 1);
        cellStyle_head.setWrapText(false);
        cellStyle_head.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle_head.setFillForegroundColor(HSSFColor.YELLOW.index);

        CellStyle cellStyle_content_0 = wb_w.createCellStyle();
        cellStyle_content_0.setAlignment(CellStyle.ALIGN_LEFT);
        headerFont = wb_w.createFont(); // 字体
        headerFont.setFontHeightInPoints((short) 9);
        headerFont.setFontName("微软雅黑");
        cellStyle_content_0.setFont(headerFont);
        cellStyle_content_0.setBorderBottom((short) 1);
        cellStyle_content_0.setBorderLeft((short) 1);
        cellStyle_content_0.setBorderRight((short) 1);
        cellStyle_content_0.setBorderTop((short) 1);
        cellStyle_content_0.setWrapText(false);
        cellStyle_content_0.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle_content_0.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);

        CellStyle cellStyle_content_1 = wb_w.createCellStyle();
        cellStyle_content_1.setAlignment(CellStyle.ALIGN_LEFT);
        headerFont = wb_w.createFont(); // 字体
        headerFont.setFontHeightInPoints((short) 9);
        headerFont.setFontName("微软雅黑");
        cellStyle_content_1.setFont(headerFont);
        cellStyle_content_1.setBorderBottom((short) 1);
        cellStyle_content_1.setBorderLeft((short) 1);
        cellStyle_content_1.setBorderRight((short) 1);
        cellStyle_content_1.setBorderTop((short) 1);
        cellStyle_content_1.setWrapText(false);

        for(int index= 0;index<sheetNames.size();index++) {
            String sheetName = sheetNames.get(index) == null ? "sheet" + index : sheetNames.get(index);

            Sheet sheet_w = wb_w.createSheet(sheetName);

            List<List<Object>> column = (columns != null && columns.size() > index) ? columns.get(index) : null;
            List<List<Object>> row = (rows != null && rows.size() > index) ? rows.get(index) : null;
            List<Merger> merger = (mergers != null && mergers.size() > index) ? mergers.get(index) : null;


            //设置列宽
            if(column != null || row != null){
                int colSize = 0;
                for(List<Object> list : column){
                    if(list != null && list.size() > colSize){
                        colSize = list.size();
                    }
                }
                for(List<Object> list : row){
                    if(list != null && list.size() > colSize){
                        colSize = list.size();
                    }
                }
                int[] colWidth = new int[colSize];
                for(List<Object> list : column){
                    if(list != null){
                        for(int i=0;i<list.size();i++){
                            String temp = ""+ list.get(i);
                            if(list.get(i) != null && list.get(i) instanceof Date){
                                temp = "yyyy-MM-dd HH:mm:ss";
                            }
                            if(temp.toCharArray().length > colWidth[i]){
                                colWidth[i] = temp.toCharArray().length;
                            }
                        }
                    }
                }
                for(List<Object> list : row){
                    if(list != null){
                        for(int i=0;i<list.size();i++){
                            String temp = ""+ list.get(i);
                            if(list.get(i) != null && list.get(i) instanceof Date){
                                temp = "yyyy-MM-dd HH:mm:ss";
                            }
                            if(temp.toCharArray().length > colWidth[i]){
                                colWidth[i] = temp.toCharArray().length;
                            }
                        }
                    }
                }
                for (int p = 0; p < colSize; p++) {
                    System.out.print(colWidth[p]);
                    sheet_w.setColumnWidth(p, (colWidth[p] == 0 ? 1 : colWidth[p]) * 300);
                }
            }

            Row row_w = null;
            int rowNum = 0;
            //设置行头
            if(column != null){
                for(int j = 0; j < column.size(); j++){
                    List<Object> list = column.get(j);
                    if(list != null){
                        row_w = sheet_w.createRow(rowNum++);
                        for(int k = 0; k < list.size(); k++){
                            Cell cell_w = row_w.createCell(k);
                            cell_w.setCellStyle(cellStyle_head);
                            Object obj = list.get(k);
                            if (obj == null) {
                                cell_w.setCellValue("");
                            } else if (obj instanceof Date) {
                                cell_w.setCellValue(formatter.format(obj));
                            } else if (obj instanceof String) {
                                cell_w.setCellValue((String) obj);
                            } else if (obj instanceof Integer) {
                                cell_w.setCellValue((Integer) obj);
                            } else if (obj instanceof Double) {
                                cell_w.setCellValue((Double) obj);
                            } else if (obj instanceof Float) {
                                cell_w.setCellValue((Float) obj);
                            } else if (obj instanceof Long) {
                                cell_w.setCellValue((Long) obj);
                            } else {
                                cell_w.setCellValue(obj.toString());
                            }
                        }
                    }

                }
            }

            //设置内容
            if(row != null ){
                for(int j = 0; j < row.size(); j++){
                    List<Object> list = row.get(j);
                    if(list != null){
                        row_w = sheet_w.createRow(rowNum++);
                        for(int k = 0; k < list.size(); k++){
                            Cell cell_w = row_w.createCell(k);
                            if (rowNum % 2 == 1) {
                                cell_w.setCellStyle(cellStyle_content_0);
                            } else {
                                cell_w.setCellStyle(cellStyle_content_1);
                            }
                            Object obj = list.get(k);
                            if (obj == null) {
                                cell_w.setCellValue("");
                            } else if (obj instanceof Date) {
                                cell_w.setCellValue(formatter.format(obj));
                            } else if (obj instanceof String) {
                                cell_w.setCellValue((String) obj);
                            } else if (obj instanceof Integer) {
                                cell_w.setCellValue((Integer) obj);
                            } else if (obj instanceof Double) {
                                cell_w.setCellValue((Double) obj);
                            } else if (obj instanceof Float) {
                                cell_w.setCellValue((Float) obj);
                            } else if (obj instanceof Long) {
                                cell_w.setCellValue((Long) obj);
                            } else {
                                cell_w.setCellValue(obj.toString());
                            }
                        }
                    }
                }
            }

            //设置合并
            if(merger != null && merger.size() > 0){
                for(Merger temp : merger){
                    if(temp != null){
                        sheet_w.addMergedRegion(new CellRangeAddress(temp.getFirstRow(), temp.getLastRow(),temp.getFirstCol(), temp.getLastCol()));
                    }
                }
            }
        }
        try {
            wb_w.write(outputstream);
            outputstream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String args[]) throws Exception{

        String excelType = ExcelUtil.XLS;
        String sheetNames = "测试sheet";
        List<List<Object>> columns = new ArrayList<List<Object>>();
        List<List<Object>> rows = new ArrayList<List<Object>>();
        List<Merger> mergers = new ArrayList<Merger>();
        OutputStream outputstream = new FileOutputStream(new File("E:/aaa.xls"));

        List<Object> column = new ArrayList<Object>();
        column.add("String");
        column.add(new Date());
        column.add(123);
        column.add(123L);
        column.add(true);
        column.add(1.233D);
        columns.add(column);

        column = new ArrayList<Object>();
        column.add("String");
        column.add(new Date());
        column.add(123);
        column.add(123L);
        column.add(true);
        column.add(1.233D);
        columns.add(column);


        column = new ArrayList<Object>();
        column.add("String");
        column.add(new Date());
        column.add(123);
        column.add(123L);
        column.add(true);
        column.add(1.233D);
        rows.add(column);

        column = new ArrayList<Object>();
        column.add("String");
        column.add(new Date());
        column.add(123);
        column.add(123L);
        column.add(true);
        column.add(1.233D);
        rows.add(column);

        column = new ArrayList<Object>();
        column.add("String");
        column.add(new Date());
        column.add(123);
        column.add(123L);
        column.add(true);
        column.add(1.233D);
        rows.add(column);

        column = new ArrayList<Object>();
        column.add("String");
        column.add(new Date());
        column.add(123);
        column.add(123L);
        column.add(true);
        column.add(1.233D);
        rows.add(column);


        ExcelUtil.exportExcel(excelType,sheetNames,columns,rows,mergers,outputstream);



    }


}



