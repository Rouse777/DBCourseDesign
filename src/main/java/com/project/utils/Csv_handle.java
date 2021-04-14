package com.project.utils;

import lombok.SneakyThrows;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


//对于一组记录的处理
@Mapper
public class Csv_handle implements IHandle{


    Class t;
    List<Object> object_lines=new ArrayList<Object>();


    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @SneakyThrows
    @Override
    public void handle(List<String> lines, String ziduan) {//缺数据清洗

         List<String> fields= Arrays.asList(ziduan.split(","));//表中的字段
         Class t=Class.forName(BigFileReader.mode);
         Constructor temp=t.getDeclaredConstructor();
         Object temp1=temp.newInstance();
         Field[] field=t.getFields();//实例中的字段

         HashMap<String,Integer> hash=new HashMap<String,Integer>();//什么字段是在第几个
         for(int i=0;i<fields.size();i++)
         {
             hash.put(fields.get(i),i);
         }

         for(int i=0;i<lines.size();i++)
         {

             String[] line=lines.get(i).split(",");
             for(Field k: field){
                 k.setAccessible(true);
                 if(k.getType().equals(Float.class))
                 {
                     k.set(temp1,Float.parseFloat(line[hash.get(k.getName())]));
                 }

                 else if(k.getType().equals(Integer.class))
                 {
                     k.set(temp1,Integer.parseInt(line[hash.get(k.getName())]));
                 }

                 else if(k.getType().equals(String.class)){
                     k.set(temp1,line[hash.get(k.getName())]);
                 }

                 else if(k.getType().equals(BigDecimal.class)){

                     k.set(temp1,BigDecimal.valueOf(Double.parseDouble(line[hash.get(k.getName())])));
                 }


             }


             object_lines.add(temp1);

         }

         insertBatch(object_lines);
    }


    @Transactional
    @SneakyThrows
    public <T> void insertBatch(List<T> target) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
//        T mapper = sqlSession.getMapper(T.class);
//        for (int i = 0; i < target.size(); i++) {
//            mapper.insertSelective(target.get(i));
//        }
//        sqlSession.flushStatements();
    }
}
