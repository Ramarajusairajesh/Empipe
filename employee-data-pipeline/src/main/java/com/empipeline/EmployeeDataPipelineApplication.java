package com.empipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class EmployeeDataPipelineApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeDataPipelineApplication.class, args);
    }
} 