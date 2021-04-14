package com.project.utils;

import java.util.ArrayList;
import java.util.List;

public interface IHandle {

    public void handle(List<String> lines,String fields)throws Exception;
    /*
    * line的格式：2020-07-20T09:55:32.480,253927-0,253927-2,38,36,38400,178（都是string类型）
    *  此函数用于对多行数据做处理（如插入数据库之类）
    * 并不能保证bulksize一定对哦
    * */
}