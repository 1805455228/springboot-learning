package com.hins.sp01hello.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StopWatch;

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
public class PoiExeelSXSSFTest {


    public static void main(String[] args) {

        createExcelData_bigFile();
        //使用xssfworkbook 会内存溢出
        //createExcelData_oom();


    }

    private static void createExcelData_bigFile() {
        HashMap<String, String> titleMap = new HashMap();
        titleMap.put("姓名", "name");
        titleMap.put("年龄", "age");
        titleMap.put("性别", "sex");
        titleMap.put("出生年月日","createDate");
        titleMap.put("身价", "singlePrice");
        titleMap.put("订单编号", "orderNo");
        titleMap.put("金额", "price");

        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<500000;i++){
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("name", "张三"+i);
            hashMap.put("age", i);
            hashMap.put("sex", "女"+i);
            hashMap.put("createDate", new Date());
            hashMap.put("singlePrice", 12.00F+i);
            hashMap.put("price", 88.58+i);
            hashMap.put("orderNo", "99907"+i);
            list.add(hashMap);
        }

        StopWatch sw = new StopWatch();
//        String exceltemplate1 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template1.xlsx";
        String exceltemplate2 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template2.xlsx";
        XSSFWorkbook workbookXlsx = null;
        SXSSFWorkbook workbook = null;
        try {
            sw.start();
            FileInputStream inputStream = new FileInputStream(exceltemplate2);
            workbookXlsx = new XSSFWorkbook(inputStream);
            workbook = new SXSSFWorkbook(workbookXlsx,500);
            // 获取sheet
            // 使用XSSFSheet（错误写法，数据量大会内存溢出）
            //Sheet sheet = workbook.getXSSFWorkbook().getSheetAt(0);
            // 应使用SXSSFSheet（避免内存溢出）(推荐)
            SXSSFSheet sheet = workbook.getSheetAt(0);
            //sheet.setForceFormulaRecalculation(true);

            Row titleRow = workbook.getXSSFWorkbook().getSheetAt(0).getRow(0);
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
                Row dataRow = sheet.getRow(j+i );
                if(null == dataRow){
                    dataRow = sheet.createRow(j+i);
                }
                //创建内容行单元格,填充数据
                for(int n=startCell;n<endCell;n++){
                    Cell titleCell = titleRow.getCell(n);
                    CellType cellType = titleCell.getCellType();
                    Cell cell = dataRow.createCell(n);
                    cell.setCellType(cellType);
                    CellStyle cellStyle = cell.getCellStyle();
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
            sw.stop();
            System.out.println("耗时1："+sw.getTotalTimeSeconds());
            
            //修改模板内容导出新模板
            sw.start();
            FileOutputStream out = new FileOutputStream("/Users/chenqixuan/Downloads/"+newFileName);
            workbook.write(out);
            out.close();
            workbook.close();
            workbook.dispose();
            sw.stop();
            System.out.println("耗时2："+sw.getTotalTimeSeconds());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认jvm内存配置20万 内存溢出oom
     */
    private static void createExcelData_oom() {
        HashMap<String, String> titleMap = new HashMap();
        titleMap.put("姓名", "name");
        titleMap.put("年龄", "age");
        titleMap.put("性别", "sex");
        titleMap.put("出生年月日","createDate");
        titleMap.put("身价", "singlePrice");
        titleMap.put("订单编号", "orderNo");
        titleMap.put("金额", "price");

        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<200000;i++){
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("name", "张三"+i);
            hashMap.put("age", i);
            hashMap.put("sex", "女"+i);
            hashMap.put("createDate", new Date());
            hashMap.put("singlePrice", 12.00F+i);
            hashMap.put("price", 88.58+i);
            hashMap.put("orderNo", "99907"+i);
            list.add(hashMap);
        }

        StopWatch sw = new StopWatch();
//        String exceltemplate1 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template1.xlsx";
        String exceltemplate2 = "/Users/chenqixuan/Documents/IntelliJIDEAWorkspace/springboot-learning/sp-01-hello/src/main/java/com/hins/sp01hello/excel/template2.xlsx";
        XSSFWorkbook workbookXlsx = null;
        SXSSFWorkbook workbook = null;
        try {
            sw.start();
            FileInputStream inputStream = new FileInputStream(exceltemplate2);
            workbookXlsx = new XSSFWorkbook(inputStream);
            workbook = new SXSSFWorkbook(workbookXlsx,1000);
            // 使用XSSFSheet（数据量大会内存溢出）
            Sheet sheet = workbook.getXSSFWorkbook().getSheetAt(0);
            // 应使用SXSSFSheet（避免内存溢出）(推荐)
            //SXSSFSheet sheet = workbook.getSheetAt(0);
            sheet.setForceFormulaRecalculation(true);

            Row titleRow = workbook.getXSSFWorkbook().getSheetAt(0).getRow(0);
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
                Row dataRow = sheet.getRow(j+i );
                if(null == dataRow){
                    dataRow = sheet.createRow(j+i);
                }
                //创建内容行单元格,填充数据
                for(int n=startCell;n<endCell;n++){
                    Cell titleCell = titleRow.getCell(n);
                    CellType cellType = titleCell.getCellType();
                    Cell cell = dataRow.createCell(n);
                    cell.setCellType(cellType);
                    CellStyle cellStyle = cell.getCellStyle();
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
            sw.stop();
            System.out.println("耗时1："+sw.getTotalTimeSeconds());

            //修改模板内容导出新模板
            sw.start();
            FileOutputStream out = new FileOutputStream("/Users/chenqixuan/Downloads/"+newFileName);
            workbook.write(out);
            out.close();
            workbook.close();
            workbook.dispose();
            sw.stop();
            System.out.println("耗时2："+sw.getTotalTimeSeconds());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
