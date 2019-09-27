package com.kclmedu.datajpa;

import com.kclmedu.datajpa.dao.LogRecordRepository;
import com.kclmedu.datajpa.entity.LogRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class HelloSpringdatajpa01Application {

    private static Logger log = LoggerFactory.getLogger(HelloSpringdatajpa01Application.class);

    public static void main(String[] args) {
        log.debug("--- begin....");
        SpringApplication.run(HelloSpringdatajpa01Application.class, args);
        log.debug("--- has been startup...");
    }

}
