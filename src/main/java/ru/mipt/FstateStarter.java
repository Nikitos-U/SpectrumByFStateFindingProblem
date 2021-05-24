package ru.mipt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FstateStarter {
    public static void main(String[] args) {
        try {
            SpringApplication.run(FstateStarter.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
