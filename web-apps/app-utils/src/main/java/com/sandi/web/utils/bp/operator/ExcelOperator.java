/**
 * $Id: ExcelOperator.java,v 1.0 17/2/16 下午2:38 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.bp.operator;

import com.sandi.web.utils.bp.Constants;
import com.sandi.web.utils.bp.entity.CfgBpTemplate;
import com.sandi.web.utils.bp.entity.CfgWriter;
import com.sandi.web.utils.bp.entity.EsBpData;
import com.sandi.web.utils.elec.ElecUitl;
import com.sandi.web.utils.elec.entity.ElecInst;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangruiping
 * @version $Id: ExcelOperator.java,v 1.1 17/2/16 下午2:38 zhangruiping Exp $
 *          Created on 17/2/16 下午2:38
 */
public class ExcelOperator extends FileOperator {

    private static final Logger logger = LoggerFactory.getLogger(ExcelOperator.class);

    /**
     * 进行文件解析
     * excel： sheet row col
     * txt     0     row col
     * cvs     0     row col
     *
     * @param cfgBpTemplateEntity
     * @param esBpDataEntity
     * @param elecInst
     * @return
     * @throws Exception
     */
    @Override
    public List<List<List<String>>> doAnalyse(CfgBpTemplate cfgBpTemplateEntity, EsBpData esBpDataEntity, ElecInst elecInst) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ElecUitl.download(elecInst, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败！", e);
            throw new Exception("文件解析时下载文件失败！");
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        List<List<List<String>>> fileData = new ArrayList<List<List<String>>>();
        Workbook wb = null;
        if (elecInst.getFileSuffix().toLowerCase().equals(".xls")) {
            wb = new HSSFWorkbook(inputStream);
        } else if (elecInst.getFileSuffix().toLowerCase().equals(".xlsx")) {
            wb = new XSSFWorkbook(inputStream);
        } else {
            throw new Exception("excel文件类型不匹配，必须为xls或xlsx");
        }
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<List<String>> rowList = new ArrayList<List<String>>();
            for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                Row row = sheet.getRow(j);
                List<String> cellList = new ArrayList<String>();
                for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
                    if (row.getCell(k) != null) {
                        switch (row.getCell(k).getCellType()) {
                            case Cell.CELL_TYPE_BLANK:
                                cellList.add("");
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                Boolean b = row.getCell(k).getBooleanCellValue();
                                cellList.add(b.toString());
                                break;
                            case Cell.CELL_TYPE_STRING:
                                cellList.add(row.getCell(k).getStringCellValue());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(row.getCell(k))) {
                                    Date date = DateUtil.getJavaDate(row.getCell(k).getNumericCellValue());
                                    cellList.add(dateformat.format(date));
                                } else {
                                    cellList.add("" + row.getCell(k).getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                cellList.add(row.getCell(k).getCellFormula());
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                cellList.add("" + row.getCell(k).getErrorCellValue());
                                break;
                        }
                    } else {
                        cellList.add(null);
                    }
                }
                rowList.add(cellList);
            }
            fileData.add(rowList);
        }
        return fileData;
    }


    /**
     * 数据生成文件
     *
     * @param headers
     * @param data
     * @param outputStream
     * @param cfgWriter
     * @throws Exception
     */
    @Override
    public void doWrite(List<List<List<String>>> headers, List<List<List<String>>> data, OutputStream outputStream, CfgWriter cfgWriter) throws Exception {
        Workbook wb_w = null;
        if (cfgWriter.getFileType() == Constants.FileType.EXCEL_XLS) {
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


        for (int index = 0; index < headers.size(); index++) {
            String sheetName = "sheet" + index;
            Sheet sheet_w = wb_w.createSheet(sheetName);

            List<List<String>> column = (headers != null && headers.size() > index) ? headers.get(index) : null;
            List<List<String>> row = (data != null && data.size() > index) ? data.get(index) : null;

            //设置列宽
            if (column != null || row != null) {
                int colSize = 0;

                for (List<String> list : column) {
                    if (list != null && list.size() > colSize) {
                        colSize = list.size();
                    }
                }
                for (List<String> list : row) {
                    if (list != null && list.size() > colSize) {
                        colSize = list.size();
                    }
                }

                int[] colWidth = new int[colSize];

                for (List<String> list : column) {
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            String temp = list.get(i);
                            if (temp.getBytes("GBK").length > colWidth[i]) {
                                colWidth[i] = temp.getBytes("GBK").length;
                            }
                        }
                    }
                }
                for (List<String> list : row) {
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            String temp = list.get(i);
                            if (temp.getBytes("GBK").length > colWidth[i]) {
                                colWidth[i] = temp.getBytes("GBK").length;
                            }
                        }
                    }
                }
                for (int p = 0; p < colSize; p++) {
                    System.out.print(colWidth[p]);
                    sheet_w.setColumnWidth(p, ((colWidth[p] == 0 ? 1 : colWidth[p]) + 1) * 300);
                }
            }

            Row row_w = null;
            int rowNum = 0;
            //设置行头
            if (column != null) {
                for (int j = 0; j < column.size(); j++) {
                    List<String> list = column.get(j);
                    if (list != null) {
                        row_w = sheet_w.createRow(rowNum++);
                        for (int k = 0; k < list.size(); k++) {
                            Cell cell_w = row_w.createCell(k);
                            if (rowNum % 2 == 1) {
                                cell_w.setCellStyle(cellStyle_content_0);
                            } else {
                                cell_w.setCellStyle(cellStyle_content_1);
                            }
                            cell_w.setCellValue(list.get(k));
                        }
                    }
                }
            }

            //设置内容
            if (row != null) {
                for (int j = 0; j < row.size(); j++) {
                    List<String> list = row.get(j);
                    if (list != null) {
                        row_w = sheet_w.createRow(rowNum++);
                        for (int k = 0; k < list.size(); k++) {
                            Cell cell_w = row_w.createCell(k);
                            if (rowNum % 2 == 1) {
                                cell_w.setCellStyle(cellStyle_content_0);
                            } else {
                                cell_w.setCellStyle(cellStyle_content_1);
                            }
                            cell_w.setCellValue(list.get(k));
                        }
                    }
                }
            }
        }
        wb_w.write(outputStream);
        outputStream.flush();
    }
}