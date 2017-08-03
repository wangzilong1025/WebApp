package com.sandi.web.utils.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qiaoyong on 2016/12/19.
 */
public class CSVUtil {

    public final static String CSV = "CSV";

    /**导出csv文件流
     * @param excelType
     * @param sheetName
     * @param columns
     * @param formatDataMap
     * @param rows
     * @param outputstream
     * @throws Exception
     */
    public static void exportCSVForDataGrid(
            String excelType,
            String sheetName,
            List<Map<String, Object>> columns,
            Map<String, Map<String, String>> formatDataMap,
            List<Map<String, Object>> rows,
            OutputStream outputstream
        ) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedWriter csvFileOutputStream = null;
        // UTF-8使正确读取分隔符","
        csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(outputstream,"GBK"), 1024);
        // 写入文件头部
        if (columns != null && columns.size() > 0) {
            for (int k = 0; k < columns.size(); k++) {
                String strTemp = "";
                if (columns.get(k).get("title") != null) {
                    strTemp = "" + columns.get(k).get("title");
                } else if (columns.get(k).get("field") != null) {
                    strTemp = "" + columns.get(k).get("field");
                } else {
                    strTemp = "";
                }
                csvFileOutputStream.write("\"" + strTemp + "\"");
                //最后一个字段不加逗号
                if ((k + 1) != columns.size()) {
                    csvFileOutputStream.write(",");
                }
            }
        }
        csvFileOutputStream.newLine();
        // 写入文件内容
        if (rows != null && rows.size() > 0) {
            for (int j = 0; j < rows.size(); j++) {
                Map<String, Object> map = rows.get(j);
                for (int k = 0; k < columns.size(); k++) {
                    String tempStr = null;
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
                                tempStr = formatDataMap.get(field).get(value);
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
                                tempStr = str;
                            } else {
                                tempStr = value;
                            }
                        } else {
                            tempStr = value;
                        }
                    } else {
                        tempStr = "";
                    }
                    csvFileOutputStream.write("\"" + tempStr + "\"");
                    //最后一个字段不加逗号
                    if ((k + 1) != columns.size()) {
                        csvFileOutputStream.write(",");
                    }
                }
                csvFileOutputStream.newLine();
            }
        }
        try {
            csvFileOutputStream.flush();
            outputstream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
