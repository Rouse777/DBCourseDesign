package com.project.utils;

import com.project.mapper.OutputMapper;
import com.project.po.*;
import com.project.po.Prbnew;
import com.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.List;



@Component
public class OutputUtils {

    @Autowired
    CellService cellservice;

    @Autowired
    KpiService kpiservice;

    @Autowired
    MrodataService mrodataservice;

    @Autowired
    PrbService prbservice;

    @Autowired
    PrbnewService prbnewservice;

    @Autowired
    OutputMapper output;

    public void output(String Dir,String filename)throws  Exception{

       //linux下换行符为/r
        String content="";
        String path=Dir+"/"+filename+".txt";
        File file=new File(path);
        BufferedWriter out = new BufferedWriter(new FileWriter(file,true),3*1024*1024);


       if(filename.equals("cell"))
       {

           List<Cell> ans=cellservice.list();
           List<String> field= output.getFields("cell");
           Class T=Cell.class;
           for(int i=0;i<field.size()-1;i++)
               content+=(field.get(i)+",");
           content+=(field.get(field.size()-1)+"/r");

           Field[] fields=T.getDeclaredFields();

           for(int i=0;i<ans.size();i++) {

               Cell temp=ans.get(i);
               for(int j=0;j<(fields.length-1);j++){

                   content+=(fields[j].get(temp).toString()+",");
               }
               content+=(fields[fields.length-1].get(temp).toString()+"/r");

           }

       }
       else if(filename.equals("kpi"))
        {
            List<Kpi> ans=kpiservice.list();
            List<String> field= output.getFields("kpi");
            Class T=Kpi.class;
            for(int i=0;i<field.size()-1;i++)
                content+=(field.get(i)+",");
            content+=(field.get(field.size()-1)+"/r");

            Field[] fields=T.getDeclaredFields();

            for(int i=0;i<ans.size();i++) {

                Kpi temp=ans.get(i);
                for(int j=0;j<(fields.length-1);j++){

                    content+=(fields[j].get(temp).toString()+",");
                }
                content+=(fields[fields.length-1].get(temp).toString()+"/r");

            }
        }
       else if(filename.equals("prb_new"))
       {
           List<Prbnew> ans=prbnewservice.list();
           List<String> field= output.getFields("prb_new");
           Class T=Prbnew.class;
           for(int i=0;i<field.size()-1;i++)
               content+=(field.get(i)+",");
           content+=(field.get(field.size()-1)+"/r");

           Field[] fields=T.getDeclaredFields();

           for(int i=0;i<ans.size();i++) {

               Prbnew temp=ans.get(i);
               for(int j=0;j<(fields.length-1);j++){

                   content+=(fields[j].get(temp).toString()+",");
               }
               content+=(fields[fields.length-1].get(temp).toString()+"/r");

           }
       }

       out.write(content);
       out.flush();
       out.close();
       return;


    }



}
