package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Entry point of this application
 *
 * <p>
 *     To discard the security auto-configuration and add our own configuration,
 *     exclude the SecurityAutoConfiguration class.
 * </p>
 *
 * @author Kisong
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
