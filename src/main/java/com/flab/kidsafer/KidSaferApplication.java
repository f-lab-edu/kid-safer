package com.flab.kidsafer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class KidSaferApplication {

    public static void main(String[] args) {
        SpringApplication.run(KidSaferApplication.class, args);
    }
}
