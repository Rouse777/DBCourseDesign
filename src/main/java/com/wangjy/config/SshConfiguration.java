package com.wangjy.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Properties;


@Component
@Slf4j
public class SshConfiguration implements ServletContextInitializer {

    public SshConfiguration() throws Exception{
        Properties p = new Properties();
        p.load(getClass().getResourceAsStream("/application.properties"));
        //如果配置文件中ssh.forward.enabled属性值为true，则使用ssh转发
        if ("true".equals(p.getProperty("ssh.forward.enabled"))) {
            log.info("SSH forward is opened.");
            log.info("SSH init ……");
            Session session = new JSch().getSession(
                    p.getProperty("ssh.forward.username"),
                    p.getProperty("ssh.forward.host"),
                    Integer.parseInt(p.getProperty("ssh.forward.port")));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(p.getProperty("ssh.forward.password"));
            session.connect();
            session.setPortForwardingL(
                    p.getProperty("ssh.forward.from_host"),
                    Integer.parseInt(p.getProperty("ssh.forward.from_port")),
                    p.getProperty("ssh.forward.to_host"),
                    Integer.parseInt(p.getProperty("ssh.forward.to_port")));
        } else {
            log.info("SSH forward is closed.");
        }

    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }
}