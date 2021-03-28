package com.project.controller;


import com.project.mapper.DataSourceConfigMapper;
import com.project.result.Result;
import com.project.utils.ShellUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Api(tags = "数据库查看及修改--需要管理员权限")
@RestController
@Slf4j
public class DataSourceConfigController {

    @Autowired
    public DataSourceConfigMapper DataSource;

    @Autowired
    public SqlSessionFactory sqlsessionfactory;

    /**
     * 三个查询提取公共部分，并使用PreparedStatement防止SQL注入
     *
     * @param variableName 要查询的数据库全局变量名
     * @return 查询结果
     */
    private String getVariable(String variableName) throws SQLException {
        try (Connection conn = sqlsessionfactory.openSession().getConnection()) {
            PreparedStatement stat = conn.prepareStatement("show global variables where variable_name like ?");
            stat.setString(1, variableName);
            ResultSet rs = stat.executeQuery();
            rs.next();
            String res = rs.getObject(2).toString();
            log.info("设置数据库变量:" + variableName + "=" + res);
            return res;
        }
    }

    @GetMapping("/admin/max_connections")
    @ApiOperation(value = "查看最大连接数")
    public Result getMaxConnections() throws Exception {
        String res = getVariable("max_connections");
        return Result.success().put("max_connections", res);
    }

    @GetMapping("/admin/wait_timeout")
    @ApiOperation(value = "查看最大空闲连接时长")
    public Result getWaitTimeout() throws Exception {
        String res = getVariable("wait_timeout");
        return Result.success().put("wait_timeout", res);
    }

    @GetMapping("/admin/innodb_buffer_pool_size")
    @ApiOperation(value = "查看缓存区大小")
    public Result getBufferSize() throws Exception {
        String res = getVariable("innodb_buffer_pool_size");
        return Result.success().put("innodb_buffer_pool_size", res);
    }

    @PostMapping("/admin/max_connections")
    @ApiOperation(value = "设置最大连接数")
    public Result setMaxConnections(@RequestParam @ApiParam("最大连接数，最低10") String value) throws Exception {
        //最低为10
        if (Integer.parseInt(value) < 10) value = "10";
        ShellUtils.execCmd("mysql -h127.0.0.1 -P3306 -uroot -proot -e \"set global max_connections=" + value + "\";");
        return Result.success();
    }

    @PostMapping("/admin/wait_timeout")
    @ApiOperation(value = "设置最大空闲连接时长")
    public Result setWaitTimeout(@RequestParam @ApiParam("最大连接时长，最低3600，单位秒") String value) throws Exception {
        //最低为3600s
        if (Integer.parseInt(value) < 3600) value = "3600";
        ShellUtils.execCmd("mysql -h127.0.0.1 -P3306 -uroot -proot -e \"set global wait_timeout=" + value + "\";");
        return Result.success();
    }

    @PostMapping("/admin/innodb_buffer_pool_size")
    @ApiOperation(value = "设置缓存区大小")
    public Result setBufferSize(@RequestParam @ApiParam("相对值，1到50之间") String value) throws Exception {
        int tmp = Integer.parseInt(value);
        tmp = tmp * 3;
        value = String.valueOf(tmp);
        value += "00000000";
        ShellUtils.execCmd("mysql -h127.0.0.1 -P3306 -uroot -proot -e \"set global innodb_buffer_pool_size=" + value + "\";");
        return Result.success();
    }

    @GetMapping("/admin/partitions")
    @ApiOperation(value = "查看所在物理分区")
    public Result getPartitions(@RequestParam String tableName) {
        List<String> partitions = DataSource.getPartition(tableName);
        if (partitions.isEmpty()) return Result.fail("该表不存在！");
        System.out.println(partitions);
        return Result.success().put("partitions", partitions.get(0));
    }
}
