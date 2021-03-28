package com.project.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component

public class Filter01 implements Filter {



    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request1, ServletResponse response1, FilterChain filterChain) throws IOException, ServletException {


        System.out.println("Corsfilter");
        HttpServletRequest request=(HttpServletRequest)request1;
        HttpServletResponse response=(HttpServletResponse)response1;


        String orignalHeader = request.getHeader("Origin");
        System.out.println(orignalHeader);

        response.addHeader("Access-Control-Allow-Origin", orignalHeader);
        System.out.println("orignalHeader:"+orignalHeader);
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));


        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
