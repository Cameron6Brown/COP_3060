package com.cameron.cop3060;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Cop3060Application {

    public static void main(String[] args) {
        System.out.println("👋 Welcome to Cameron Brown's COP_3060 App");
        System.out.println("📁 Repo: https://github.com/Cameron6Brown/COP_3060");
        SpringApplication.run(Cop3060Application.class, args);
        System.out.println("✅ Server running at http://localhost:8080");
    }
}
