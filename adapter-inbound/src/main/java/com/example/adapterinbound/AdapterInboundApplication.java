
package com.example.adapterinbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class AdapterInboundApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdapterInboundApplication.class, args);
    }
}
