package com.bohdan.pr6b;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
public class Pr6bApplication implements CommandLineRunner {
    @Autowired
    private TaskService taskService;

    public static void main(String[] args) {
        SpringApplication.run(Pr6bApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        taskService.executeTaskWithRetries();
    }
}
