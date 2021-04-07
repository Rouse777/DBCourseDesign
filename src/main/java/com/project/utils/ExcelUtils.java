package com.project.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel工具类，poi解析Excel文件,并利用反射返回封装类的列表
 *
 * @Author 王景扬
 */
@Slf4j
public class ExcelUtils {
    @SneakyThrows
    public static <T> List<T> getListByExcel(MultipartFile file, Class<T> klass) {
        String extName = getExcelExtName(file.getOriginalFilename());
        if (extName == null) return null;
        return getListByExcel(file.getInputStream(), extName, klass);
    }

    @SneakyThrows
    public static <T> List<T> getListByExcel(InputStream in, String extName, Class<T> klass) {

        List<T> list = new ArrayList<>();

        // 创建excel工作簿
        Workbook work = getWorkbook(in, extName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = work.getSheetAt(0);//默认只有一张表
        if (sheet == null) return null;
        List<Field> fieldList=getDefaultFields(klass);
        // 滤过第一行标题
        for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            //根据表头获取封装类的setter方法
            if (r == row.getFirstCellNum()) {
                fieldList=getFieldsByRow(row,klass);
            }
            list.add(getObjFromRow(row, fieldList,klass));
        }

        work.close();
        return list;
    }
    private static <T> List<Field> getDefaultFields(Class<T> klass){
        List<Field> list=new ArrayList<>();
        for(Field field:klass.getFields()){
            if(field.isAnnotationPresent(TableId.class)
                    ||field.isAnnotationPresent(TableField.class))list.add(field);
        }
        return list;
    }

    private static <T> List<Field> getFieldsByRow(Row row,Class<T> klass) {
        Map<String, Field> fieldMap = createFieldMap(klass);
        List<Field> fieldList=new ArrayList<>();
        for(Cell cell:getCells(row)){
            String fieldInExcel=cell.getStringCellValue();
            fieldList.add(fieldMap.getOrDefault(fieldInExcel, null));
        }
        return fieldList;
    }

    private static <T> Map<String,Field> createFieldMap(Class<T> klass) {
        HashMap<String, Field> map = new HashMap<>();
        for(Field field:klass.getFields()){
            //获取TableId或TableField注解
            TableId tableIdAnnotation = field.getAnnotation(TableId.class);
            TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
            if(tableIdAnnotation!=null){
                map.put(tableIdAnnotation.value(),field);
            }else if(tableFieldAnnotation!=null){
                map.put(tableFieldAnnotation.value(),field);
            }else {
                map.put(field.getName(),field);
            }
        }
        return map;
    }

    @SneakyThrows
    private static <T> T getObjFromRow(Row row, List<Field> fieldList,Class<T> klass) {
        T obj = klass.getConstructor().newInstance();
        Field[] fields = klass.getFields();
        List<Cell> cellList = getCells(row);
        if(cellList.size()!=fieldList.size()){
            log.warn("excel转换错误，字段数不一致");
            return null;
        }
        for(int i=0;i<cellList.size();i++){
            Field field = fieldList.get(i);
            field.getGenericType()
        }
        return null;
    }

    private static Method findSetter(Field[] fields) {
        return null;
    }

    private static List<Cell> getCells(Row row) {
        List<Cell> list = new ArrayList<>();
        for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
            list.add(row.getCell(c));
        }
        return list;
    }

    /**
     * 获取扩展名.xls或.xlsx，其他扩展名返回null
     */
    public static String getExcelExtName(String fileName) {
        if (fileName == null) return null;
        int idx = fileName.lastIndexOf('.');
        if (idx < 0) return null;
        return fileName.substring(idx);
    }

    public static boolean isExcelName(String fileName) {
        return getExcelExtName(fileName) != null;
    }

    @SneakyThrows
    private static Workbook getWorkbook(InputStream in, String extName) {
        if (".xls".equals(extName)) {
            return new HSSFWorkbook(in);
        } else if (".xlsx".equals(extName)) {
            return new XSSFWorkbook(in);
        }
        return null;
    }

}

