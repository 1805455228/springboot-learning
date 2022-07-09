package com.hins.sp01hello.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qixuan.chen
 * @date 2022/7/8 19:46
 */
public class PoiExeelTest {


    public static void main(String[] args) {
        //createExcelData();
        createExcelData2();
        createExcelData3();


    }

    private static void createExcelData3() {
        HashMap<String, String> titleMap = new HashMap();
        titleMap.put("姓名", "name");
        titleMap.put("年龄", "age");
        titleMap.put("性别", "sex");
        titleMap.put("出生年月日","createDate");
        titleMap.put("身价", "singlePrice");
        titleMap.put("订单编号", "orderNo");
        titleMap.put("金额", "price");

        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("name", "张三");
        hashMap.put("age", 18);
        hashMap.put("sex", "女");
        hashMap.put("createDate", new Date());
        hashMap.put("singlePrice", 12.00F);
        hashMap.put("price", 88.58);
        hashMap.put("orderNo", "99907");
        list.add(hashMap);
        HashMap<String, Object> hashMap1 = new HashMap();
        hashMap1.put("name", "李四");
        hashMap1.put("age", 16);
        hashMap1.put("sex", "男");
        hashMap1.put("createDate", new Date());
        hashMap1.put("singlePrice", 123.77);
        hashMap1.put("price", 90.58);
        hashMap1.put("orderNo", "34364575476587");
        list.add(hashMap1);

//        String exceltemplate1 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template1.xlsx";
        String exceltemplate2 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template2.xlsx";
        XSSFWorkbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(exceltemplate2);
            workbook = new XSSFWorkbook(inputStream);
            // 获取sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            sheet.setForceFormulaRecalculation(true);

            XSSFRow titleRow = sheet.getRow(0);
            if(null == titleRow){
                titleRow = sheet.createRow(0);
            }
            //记录标题行单元格
            HashMap<Integer, String> titleIndexMap = new HashMap();
            short startCell = titleRow.getFirstCellNum();
            short endCell = titleRow.getLastCellNum();
            for(int m= startCell;m<endCell;m++){
                titleIndexMap.put(m,titleRow.getCell(m).getStringCellValue());
            }


            int j = 1;//去除标题行
            //填充数据
            for (int i = 0; i < list.size() ; i++) {
                //创建内容行
                XSSFRow dataRow = sheet.getRow(j+i );
                if(null == dataRow){
                    dataRow = sheet.createRow(j+i);
                }
                //创建内容行单元格,填充数据
                for(int n=startCell;n<endCell;n++){
                    XSSFCell titleCell = titleRow.getCell(n);
                    CellType cellType = titleCell.getCellType();
                    XSSFCell cell = dataRow.createCell(n);
                    cell.setCellType(cellType);
                    XSSFCellStyle cellStyle = cell.getCellStyle();
                    //设置格式
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置水平居中
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直居中
                    //cell.setCellStyle(cellStyle);

                    String titleKey = titleIndexMap.get(n);//根据下标获取标题文本
                    String key = titleMap.get(titleKey);//根据标题文本货物属性名
                    Object fieldValue = list.get(i).get(key);//根据属性获取value
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //cell.setCellValue(String.valueOf(obj));
                    if (fieldValue instanceof Integer) {
                        cell.setCellValue((Integer) fieldValue);
                    } else if (fieldValue instanceof Long) {
                        cell.setCellValue((Long) fieldValue);
                    } else if (fieldValue instanceof Double) {
                        cell.setCellValue((Double) fieldValue);
                    } else if (fieldValue instanceof Double) {
                        cell.setCellValue((Double) fieldValue);
                    } else if (fieldValue instanceof Boolean) {
                        cell.setCellValue((Boolean) fieldValue);
                    } else if (fieldValue instanceof Date) {
                        Date date = (Date) fieldValue;
                        String dateStr = formatter.format(date);
                        cell.setCellValue(dateStr);
                    } else {
                        cell.setCellValue(String.valueOf(fieldValue));
                    }

                }
            }
            String newFileName = UUID.randomUUID().toString() + "_" + "送货单" + ".xlsx";

            //修改模板内容导出新模板
            FileOutputStream out = new FileOutputStream("/Users/chenqixuan/Downloads/"+newFileName);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createExcelData2() {
        HashMap<String, String> titleMap = new HashMap();
        titleMap.put("姓名", "name");
        titleMap.put("年龄", "age");
        titleMap.put("性别", "sex");
        titleMap.put("出生年月日","createDate");
        titleMap.put("身价", "singlePrice");

        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("name", "张三");
        hashMap.put("age", 18);
        hashMap.put("sex", "女");
        hashMap.put("createDate", new Date());
        hashMap.put("singlePrice", 12.00F);
        list.add(hashMap);
        HashMap<String, Object> hashMap1 = new HashMap();
        hashMap1.put("name", "李四");
        hashMap1.put("age", 16);
        hashMap1.put("sex", "男");
        hashMap1.put("createDate", new Date());
        hashMap1.put("singlePrice", 123.77);
        list.add(hashMap1);

        String exceltemplate1 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template1.xlsx";
        String exceltemplate2 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template2.xlsx";
        XSSFWorkbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(exceltemplate1);
            workbook = new XSSFWorkbook(inputStream);
            // 获取sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            sheet.setForceFormulaRecalculation(true);

            XSSFRow titleRow = sheet.getRow(0);
            if(null == titleRow){
                titleRow = sheet.createRow(0);
            }
            //记录标题行单元格
            HashMap<Integer, String> titleIndexMap = new HashMap();
            short startCell = titleRow.getFirstCellNum();
            short endCell = titleRow.getLastCellNum();
            for(int m= startCell;m<endCell;m++){
                titleIndexMap.put(m,titleRow.getCell(m).getStringCellValue());
            }


            int j = 1;//去除标题行
            //填充数据
            for (int i = 0; i < list.size() ; i++) {
                //创建内容行
                XSSFRow dataRow = sheet.getRow(j+i );
                if(null == dataRow){
                    dataRow = sheet.createRow(j+i);
                }
                //创建内容行单元格,填充数据
                for(int n=startCell;n<endCell;n++){
                    XSSFCell titleCell = titleRow.getCell(n);
                    CellType cellType = titleCell.getCellType();
                    XSSFCell cell = dataRow.createCell(n);
                    cell.setCellType(cellType);
                    XSSFCellStyle cellStyle = cell.getCellStyle();
                    //设置格式
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置水平居中
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直居中
                    //cell.setCellStyle(cellStyle);

                    String titleKey = titleIndexMap.get(n);//根据下标获取标题文本
                    String key = titleMap.get(titleKey);//根据标题文本货物属性名
                    Object fieldValue = list.get(i).get(key);//根据属性获取value
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //cell.setCellValue(String.valueOf(obj));
                    if (fieldValue instanceof Integer) {
                        cell.setCellValue((Integer) fieldValue);
                    } else if (fieldValue instanceof Long) {
                        cell.setCellValue((Long) fieldValue);
                    } else if (fieldValue instanceof Double) {
                        cell.setCellValue((Double) fieldValue);
                    } else if (fieldValue instanceof Double) {
                        cell.setCellValue((Double) fieldValue);
                    } else if (fieldValue instanceof Boolean) {
                        cell.setCellValue((Boolean) fieldValue);
                    } else if (fieldValue instanceof Date) {
                        Date date = (Date) fieldValue;
                        String dateStr = formatter.format(date);
                        cell.setCellValue(dateStr);
                    } else {
                        cell.setCellValue(String.valueOf(fieldValue));
                    }

                }
            }
            String newFileName = UUID.randomUUID().toString() + "_" + "送货单" + ".xlsx";

            //修改模板内容导出新模板
            FileOutputStream out = new FileOutputStream("/Users/chenqixuan/Downloads/"+newFileName);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void createExcelData() {
        HashMap<String, String> titleMap = new HashMap();
        titleMap.put("姓名", "name");
        titleMap.put("年龄", "age");
        titleMap.put("性别", "sex");
        titleMap.put("出现日期","createDate");
        titleMap.put("身价", "singlePrice");

        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("name", "张三");
        hashMap.put("age", 18);
        hashMap.put("sex", "女");
        hashMap.put("createDate", new Date());
        hashMap.put("singlePrice", 12.00F);
        list.add(hashMap);
        HashMap<String, Object> hashMap1 = new HashMap();
        hashMap1.put("name", "李四");
        hashMap1.put("age", 16);
        hashMap1.put("sex", "男");
        hashMap1.put("createDate", new Date());
        hashMap1.put("singlePrice", 123.77);
        list.add(hashMap1);

        String exceltemplate1 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template1.xlsx";
        String exceltemplate2 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template2.xlsx";
        XSSFWorkbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(exceltemplate1);
            workbook = new XSSFWorkbook(inputStream);
            // 获取sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            sheet.setForceFormulaRecalculation(true);

            XSSFRow titleRow = sheet.getRow(0);
            if(null == titleRow){
                titleRow = sheet.createRow(0);
            }
            //记录标题行单元格
            HashMap<Integer, String> titleIndexMap = new HashMap();
            short startCell = titleRow.getFirstCellNum();
            short endCell = titleRow.getLastCellNum();
            for(int m= startCell;m<endCell;m++){
                titleIndexMap.put(m,titleRow.getCell(m).getStringCellValue());
            }


            int j = 1;//去除标题行
            //填充数据
            for (int i = 0; i < list.size() ; i++) {
                //创建内容行
                XSSFRow dataRow = sheet.getRow(j+i );
                if(null == dataRow){
                    dataRow = sheet.createRow(j+i);
                }
                //创建内容行单元格,填充数据
                for(int n=startCell;n<endCell;n++){
                    XSSFCell titleCell = titleRow.getCell(n);
                    CellType cellType = titleCell.getCellType();

                    XSSFCell cell = dataRow.createCell(n);
                    cell.setCellType(cellType);

                    String titleKey = titleIndexMap.get(n);//根据下标获取标题文本
                    String key = titleMap.get(titleKey);//根据标题文本货物属性名
                    Object fieldValue = list.get(i).get(key);//根据属性获取value
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //cell.setCellValue(String.valueOf(obj));
                    if (fieldValue instanceof Integer) {
                        cell.setCellValue((Integer) fieldValue);
                    } else if (fieldValue instanceof Long) {
                        cell.setCellValue((Long) fieldValue);
                    } else if (fieldValue instanceof Double) {
                        cell.setCellValue((Double) fieldValue);
                    } else if (fieldValue instanceof Double) {
                        cell.setCellValue((Double) fieldValue);
                    } else if (fieldValue instanceof Boolean) {
                        cell.setCellValue((Boolean) fieldValue);
                    } else if (fieldValue instanceof Date) {
                        Date date = (Date) fieldValue;
                        String dateStr = formatter.format(date);
                        cell.setCellValue(dateStr);
                    } else {
                        cell.setCellValue(String.valueOf(fieldValue));
                    }

                }
            }
            String newFileName = UUID.randomUUID().toString() + "_" + "送货单" + ".xlsx";

            //修改模板内容导出新模板
            FileOutputStream out = new FileOutputStream("/Users/chenqixuan/Downloads/"+newFileName);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取excel数据的单元格数据类型转换
    private String getCellValue(XSSFCell cell){
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#.##");
        if(cell.getCellType() == CellType.NUMERIC){
            if (DateUtil.isCellDateFormatted(cell)){
                Date date = cell.getDateCellValue();
                //格式转换
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cellValue = sdf.format(date);
            }else {
                cellValue = df.format(cell.getNumericCellValue()).toString();
            }
        }else if (cell.getCellType() == CellType.STRING){
            cellValue = cell.getStringCellValue();
        }else if (cell.getCellType() == CellType.FORMULA){
            cellValue = cell.getCellFormula();
        }else if (cell.getCellType() == CellType.BLANK){
            cellValue = "";
        }else if (cell.getCellType() == CellType.BOOLEAN){
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
        }
        return cellValue;
    }
}
