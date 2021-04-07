package com.project.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * excel工具类，poi解析Excel文件,并利用反射返回封装类的列表
 *
 * @Author 王景扬
 */
@Slf4j
public class ExcelUtils {
    /**
     * @param file excel格式的文件
     * @param klass 封装类对象
     * @return 封装类的列表
     */
    @SneakyThrows
    public static <T> List<T> getListByExcel(MultipartFile file, Class<T> klass) {
        String extName = getExcelExtName(file.getOriginalFilename());
        if (extName == null) return null;
        return getListByExcel(file.getInputStream(), extName, klass);
    }

    /**
     * @param in 输入流
     * @param extName excel的扩展名，xls或xlsx
     * @param klass 封装类对象
     * @return 封装类的列表
     */
    @SneakyThrows
    public static <T> List<T> getListByExcel(InputStream in, String extName, Class<T> klass) {

        List<T> list = new ArrayList<>();

        // 创建excel工作簿
        try(Workbook work = getWorkbook(in, extName)){
            if (null == work) {
                throw new Exception("创建Excel工作薄为空！");
            }
            Sheet sheet = work.getSheetAt(0);//默认只有一张表
            if (sheet == null) return null;
            List<Field> fieldList = getDefaultFields(klass);
            for (int r = sheet.getFirstRowNum(); r < sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                //根据表头按顺序获取封装类对应的Field对象
                if (r == row.getFirstCellNum()) {
                    fieldList = getFieldsByRow(row, klass);
                    /*for(Field field:fieldList){
                        System.out.println(field);
                    }*/
                    continue;
                }
                //根据文件一行的记录和fieldList获得封装类
                list.add(getObjFromRow(row, fieldList, klass));
            }
        }
        return list;
    }

    private static <T> List<Field> getDefaultFields(Class<T> klass) {
        List<Field> list = new ArrayList<>();
        for (Field field : klass.getFields()) {
            if (field.isAnnotationPresent(TableId.class)
                    || field.isAnnotationPresent(TableField.class)) list.add(field);
        }
        return list;
    }

    private static <T> List<Field> getFieldsByRow(Row row, Class<T> klass) {
        Map<String, Field> fieldMap = createFieldMap(klass);
        List<Field> fieldList = new ArrayList<>();
        for (Cell cell : getCells(row)) {
            String fieldInExcel = cell.getStringCellValue();
            fieldList.add(fieldMap.getOrDefault(fieldInExcel, null));
        }
        return fieldList;
    }

    private static <T> Map<String, Field> createFieldMap(Class<T> klass) {
        HashMap<String, Field> map = new HashMap<>();
        for (Field field : klass.getDeclaredFields()) {
            //为了访问private字段
            field.setAccessible(true);
            //获取TableId或TableField注解
            TableId tableIdAnnotation = field.getAnnotation(TableId.class);
            TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
            if (tableIdAnnotation != null) {
                map.put(tableIdAnnotation.value(), field);
            } else if (tableFieldAnnotation != null) {
                map.put(tableFieldAnnotation.value(), field);
            } else {
                map.put(field.getName(), field);
            }
        }
        return map;
    }


    @SneakyThrows
    private static <T> T getObjFromRow(Row row, List<Field> fieldList, Class<T> klass) {
        //用空构造器获取对象实例
        T instance = klass.getConstructor().newInstance();
        List<Cell> cellList = getCells(row);
        if (cellList.size() != fieldList.size()) {
            log.warn("excel转换错误，字段数不一致");
            return null;
        }
        for (int i = 0; i < cellList.size(); i++) {
            //分析每个cell类型并转换，然后赋值给实例的对应属性
            Field field = fieldList.get(i);
            Cell cell = cellList.get(i);
            Class<?> type = field.getType();
            if (type == Integer.class) {
                field.set(instance,getIntegerFromCell(cell));
            } else if (type == Float.class) {
                field.set(instance,getFloatFromCell(cell));
            } else if (type == String.class) {
                field.set(instance,getStringFromCell(cell));
            } else {
                log.warn("无法赋值的类型：{}", type.getName());
            }
        }
        return instance;
    }

    private static Integer getIntegerFromCell(Cell cell){
        CellType type = cell.getCellType();
        if(type==CellType.NUMERIC){
            return (int)cell.getNumericCellValue();
        }
        else if(type==CellType.STRING){
            return Integer.parseInt(cell.getStringCellValue());
        }else if(type==CellType.FORMULA){
            return null;
        }else{
            log.warn("不能处理的cell类型:{}",cell.getCellType());
            return null;
        }
    }
    private static Float getFloatFromCell(Cell cell){
        CellType type = cell.getCellType();
        if(type==CellType.NUMERIC){
            return (float)cell.getNumericCellValue();
        }
        else if(type==CellType.STRING){
            return Float.parseFloat(cell.getStringCellValue());
        }else if(type==CellType.FORMULA){
            return null;
        }else{
            log.warn("不能处理的cell类型:{}",cell.getCellType());
            return null;
        }
    }
    private static String getStringFromCell(Cell cell){
        return cell.getStringCellValue();
    }

    private static List<Cell> getCells(Row row) {
        List<Cell> list = new ArrayList<>();
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
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

