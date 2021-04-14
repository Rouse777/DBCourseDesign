package com.project.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.project.po.*;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


//对于一组记录的处理
@Mapper
public class Csv_handle implements IHandle {


    @Override
    public void handle(List<String> lines, String ziduan) throws Exception {//缺数据清洗
        System.out.println(lines.size());
        List<String> fields = Arrays.asList(ziduan.split(","));//表中的字段
        Class t = Class.forName("com.project.po." + BigFileReader.mode);
        Constructor temp = t.getDeclaredConstructor();
        Field[] field = t.getDeclaredFields();//实例中的字段

        HashMap<String, Integer> hash = new HashMap<String, Integer>();//什么字段是在第几个
        for (int i = 0; i < fields.size(); i++) {
            hash.put(fields.get(i), i);

        }

//        for(String key: hash.keySet()){
//            System.out.println(key+": "+hash.get(key));
//        }


        class insertbatch {

            public <T> void process(List<T> object_lines, T temp1) throws Exception {

                for (int i = 0; i < lines.size(); i++) {

                    String[] line = lines.get(i).split(",");

                    for (Field k : field) {
                        k.setAccessible(true);
                        if (k.getName().equals("serialVersionUID")) continue;
                        String cur;
                        TableId tableIdAnnotation = k.getAnnotation(TableId.class);
                        TableField tableFieldAnnotation = k.getAnnotation(TableField.class);
                        if (tableIdAnnotation != null) {
                            cur = tableIdAnnotation.value();
                        } else if (tableFieldAnnotation != null) {
                            cur = tableFieldAnnotation.value();
                        } else {
                            cur = k.getName();
                        }


                        if (k.getType().equals(Float.class)) {
                            k.set(temp1, Float.parseFloat(line[hash.get(cur)]));
//                            System.out.println(line[hash.get(cur)]);
                        } else if (k.getType().equals(Integer.class)) {
                            k.set(temp1, Integer.parseInt(line[hash.get(cur)]));
//                            System.out.println(line[hash.get(cur)]);
                        } else if (k.getType().equals(String.class)) {
                            k.set(temp1, line[hash.get(cur)]);
//                            System.out.println(line[hash.get(cur)]);
                        } else if (k.getType().equals(BigDecimal.class)) {

                            k.set(temp1, BigDecimal.valueOf(Double.parseDouble(line[hash.get(cur)])));
//                            System.out.println(line[hash.get(cur)]);
                        }


                    }


                    object_lines.add(temp1);

                }
//                cleanAndSaveBatch(object_lines);
//                  System.out.println(object_lines);


            }

        }

        if (t.equals(Cell.class)) {
            List<Cell> object_lines = new ArrayList<Cell>();
            Cell temp1 = new Cell();
            new insertbatch().process(object_lines, temp1);

        } else if (t.equals(Kpi.class)) {
            List<Kpi> object_lines = new ArrayList<Kpi>();
            Kpi temp1 = new Kpi();
            new insertbatch().process(object_lines, temp1);

        } else if (t.equals(Mrodata.class)) {
            List<Mrodata> object_lines = new ArrayList<Mrodata>();
            Mrodata temp1 = new Mrodata();
            new insertbatch().process(object_lines, temp1);

        } else if (t.equals(Prb.class)) {
            List<Prb> object_lines = new ArrayList<Prb>();
            Prb temp1 = new Prb();
            new insertbatch().process(object_lines, temp1);

        } else if (t.equals(Prbnew.class)) {
            List<Prbnew> object_lines = new ArrayList<Prbnew>();
            Prbnew temp1 = new Prbnew();
            new insertbatch().process(object_lines, temp1);

        }


    }

}
