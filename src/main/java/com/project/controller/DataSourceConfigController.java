package com.project.controller;


import com.project.mapper.DataSourceConfigMapper;
import com.project.result.Result;
import com.project.ssh.ShellUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.rmi.Remote;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Api(tags = "数据库查看及修改--需要管理员权限")
@RestController
public class DataSourceConfigController {

   @Autowired
   public DataSourceConfigMapper DataSource;


   @Autowired
   public SqlSessionFactory sqlsessionfactory;

   Connection conn;


   @PostMapping("/admin/max_connections")
   @ApiOperation(value = "最大连接数")

   public Result abc() throws Exception
   {
      conn=sqlsessionfactory.openSession().getConnection();
      Statement stat=conn.createStatement();
      ResultSet rs = stat.executeQuery(" show global variables where variable_name like 'max_connections'");
//      while(rs.next()){
//      System.out.println(rs.getObject(1));
//      System.out.println(rs.getObject(2));}
//      Map<String,Long> a=DataSource.getMax_connections().get("@@session");
       rs.next();
       String ans=rs.getObject(2).toString();
       conn.close();
      return Result.success(ans);
   }



   @PostMapping("/admin/wait_timeout")
   @ApiOperation(value = "最大空闲连接时长")

   public Result abc1() throws Exception
   {
      conn=sqlsessionfactory.openSession().getConnection();
      Statement stat=conn.createStatement();
      ResultSet rs = stat.executeQuery(" show global variables where variable_name like 'wait_timeout'");
      rs.next();
      String ans=rs.getObject(2).toString();
      conn.close();
      return Result.success(ans);
   }

   @PostMapping("/admin/innodb_buffer_pool_size")
   @ApiOperation(value = "缓存区大小")

   public Result abc2() throws Exception
   {
      conn=sqlsessionfactory.openSession().getConnection();
      Statement stat=conn.createStatement();
      ResultSet rs = stat.executeQuery(" show global variables where variable_name like 'innodb_buffer_pool_size'");
      rs.next();
      String ans=rs.getObject(2).toString();
      conn.close();
      return Result.success(ans);
   }

   @RequestMapping("/admin/set_max_connections")
   @ApiOperation(value = "设置最大连接数--最低为10")
   @ApiModelProperty(value = "参数写在url上")

   public Result cba(@RequestParam String value) throws Exception
   {
      ShellUtils.execCmd("mysql -h127.0.0.1 -P3306 -uroot -proot -e \"set global max_connections="+value+"\";");
      return Result.success("ok");
   }



   @RequestMapping("/admin/set_wait_timeout")
   @ApiOperation(value = "设置最大空闲连接时长--最低为1000")

   public Result cba1(@RequestParam String value)throws Exception
   {
      ShellUtils.execCmd("mysql -h127.0.0.1 -P3306 -uroot -proot -e \"set global wait_timeout="+value+"\";");
      return Result.success("ok");
   }

   @RequestMapping("/admin/set_innodb_buffer_pool_size")
   @ApiModelProperty(value="传的是相对量非真实值，从1到50")
   @ApiOperation(value = "设置缓存区大小--1到50之间")

   public Result cba3(@RequestParam String value) throws Exception
   {
      int tmp=Integer.parseInt(value);
      tmp=tmp*3;
      value=String.valueOf(tmp);
      value+="00000000";
      ShellUtils.execCmd("mysql -h127.0.0.1 -P3306 -uroot -proot -e \"set global innodb_buffer_pool_size="+value+"\";");
      return Result.success("ok");
   }

   @RequestMapping("/admin/partitions")
   @ApiOperation(value = "所在物理分区")

   public Result cba2(@RequestParam String name)
   {
      return Result.success(DataSource.getPartition(name).get(0));
   }












}
