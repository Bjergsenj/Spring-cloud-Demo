package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

//@SpringCloudApplication包括：@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker
@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
