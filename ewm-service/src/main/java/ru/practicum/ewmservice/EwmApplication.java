package ru.practicum.ewmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "ru.practicum.ewmservice", "ru.practicum.statisticclient"})
public class EwmApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwmApplication.class, args);
    }

}
