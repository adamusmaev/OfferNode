package com;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j
public class ActuatorServiceApplication {
    public static void main(String[] args) {
        log.info("Start application");
        SpringApplication.run(ActuatorServiceApplication.class, args);
    }
}
