package com.yutong.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ExcelUtils {

    public static List<JSONArray> readExcel(String filePath) {
        List<JSONArray> sheetList = new ArrayList<JSONArray>();
        InputStream inputStream = null;
        try {
            filePath = URLDecoder.decode(filePath, "UTF-8");
            inputStream = new FileInputStream(new File(filePath));
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            Workbook workBook = null;
            if (".xls".equals(suffix)) { // 97-03
                // POIFSFileSystem fs = new POIFSFileSystem(is);
                workBook = new HSSFWorkbook(inputStream);
            }
            else if (".xlsx".equals(suffix)) { // 2007
                workBook = new XSSFWorkbook(inputStream);
            }

            if (workBook == null) {
                return sheetList;
            }

            int numberOfSheets = workBook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                JSONArray rowJSONArray = new JSONArray();
                Sheet sheet = workBook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }

                int rowCount = sheet.getPhysicalNumberOfRows(); // 获得行数
                if (rowCount <= 0) {
                    continue;
                }
                for (int j = 0; j < rowCount; j++) {
                    Row row = sheet.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    int cellCount = row.getLastCellNum();// 获得列数
                    if (cellCount <= 0) {
                        continue;
                    }

                    JSONObject rowJsonObject = new JSONObject();
                    for (int k = 0; k < cellCount; k++) {
                        Cell cell = row.getCell(k);
                        if (cell == null) {
                            continue;
                        }

                        String cellIndexToColumn = indexToColumn(k + 1);

                        // cell.setCellType(Cell.CELL_TYPE_STRING);
                        String cellValue = cell.getStringCellValue();
                        rowJsonObject.put(cellIndexToColumn + (j + 1),
                                cellValue);
                    }
                    rowJSONArray.add(rowJsonObject);
                }
                sheetList.add(rowJSONArray);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        return sheetList;
    }


    /**
     * 
     * 用于将Excel表格中列号字母转成列索引，从1对应A开始
     * 
     * @param column
     *        列号
     * @return 列索引
     */

    public static int columnToIndex(String column) {
        if (!column.matches("[A-Z]+")) {
            try {
                throw new Exception("Invalid parameter");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        int index = 0;
        char[] chars = column.toUpperCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            index += (chars[i] - 'A' + 1)
                    * (int) Math.pow(26, chars.length - i - 1);
        }
        return index;

    }


    /**
     * 用于将excel表格中列索引转成列号字母，从A对应1开始
     * 
     * @param index
     *        列索引
     * @return 列号
     */
    public static String indexToColumn(int index) {
        if (index <= 0) {
            try {
                throw new Exception("Invalid parameter");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        index--;
        String column = "";
        do {
            if (column.length() > 0) {
                index--;
            }
            column = ((char) (index % 26 + 'A')) + column;
            index = (index - index % 26) / 26;
        } while (index > 0);

        return column;

    }

}
