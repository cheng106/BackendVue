package com.mark.cheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ChengApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChengApplication.class, args);
    }

}
