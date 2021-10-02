package com.tjhd.drawandguess;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
@MapperScan(basePackages = "com.tjhd.drawandguess.mapper")
public class DrawAndGuessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrawAndGuessApplication.class, args);
    }

}
