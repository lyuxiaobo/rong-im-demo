package com.lemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author LyuBo
 * @create 2021/6/12 21:42
 */
@SpringBootApplication
public class IMApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(IMApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IMApplication.class);
    }
}
