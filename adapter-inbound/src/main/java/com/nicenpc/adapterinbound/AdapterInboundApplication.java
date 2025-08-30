package com.nicenpc.adapterinbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nicenpc")
public class AdapterInboundApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdapterInboundApplication.class, args);
    }
}
