package com.pinux.springbootmultithread;

import com.pinux.springbootmultithread.util.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.pinux.springbootmultithread.mapper")
public class SpringBootMultiThreadApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(SpringBootMultiThreadApplication.class, args);
        SpringUtils.setApplicationContext(context);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootMultiThreadApplication.class);
    }

}
