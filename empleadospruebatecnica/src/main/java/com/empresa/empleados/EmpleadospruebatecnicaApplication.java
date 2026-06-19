package com.empresa.empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmpleadospruebatecnicaApplication {


    public static void main(String[] args) {
        System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("123456"));
        SpringApplication.run(EmpleadospruebatecnicaApplication.class, args);
    }
}
