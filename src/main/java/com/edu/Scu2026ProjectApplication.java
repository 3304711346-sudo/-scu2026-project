package com.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.edu.mapper")
public class Scu2026ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Scu2026ProjectApplication.class, args);
    }

}
