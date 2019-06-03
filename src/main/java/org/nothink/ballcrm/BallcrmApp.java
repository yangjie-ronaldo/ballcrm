package org.nothink.ballcrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("org.nothink.jaycrm.mapper")
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class BallcrmApp {

    public static void main(String[] args) {
        SpringApplication.run(BallcrmApp.class, args);
    }
}
