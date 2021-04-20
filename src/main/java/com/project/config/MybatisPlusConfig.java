package com.project.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    public MybatisPlusConfig() {
        //不同jar包的多xml解析器冲突
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
    }

    //配置分页插件
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 这是分页拦截器
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        innerInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        innerInterceptor.setMaxLimit(500L);
        mybatisPlusInterceptor.addInnerInterceptor(innerInterceptor);
        return mybatisPlusInterceptor;
    }
}
