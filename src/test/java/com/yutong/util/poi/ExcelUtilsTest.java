package com.yutong.util.poi;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ExcelUtilsTest {

    public static void main(String[] args) {
        String filePath = "D:\\temp\\03.框架验证系统\\04. BD系统设计\\规则字段映射表.xls";
        List<JSONArray> sheetList = ExcelUtils.readExcel(filePath);
        /* 取得第一个sheet “字段代码对照表” */
        JSONArray jsonArray = sheetList.get(0);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
        }
    }

}
