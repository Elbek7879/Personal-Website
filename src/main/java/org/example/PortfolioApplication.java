package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortfolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
        System.out.println("===========================================");
        System.out.println("✅ Portfolio Backend is running!");
        System.out.println("📍 API: http://localhost:8080/api");
        System.out.println("🗄️ H2 Console: http://localhost:8080/h2-console");
        System.out.println("===========================================");
    }
}