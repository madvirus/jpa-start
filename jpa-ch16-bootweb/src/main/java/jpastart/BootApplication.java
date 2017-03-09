package jpastart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BootApplication.class, args);
    }
}
