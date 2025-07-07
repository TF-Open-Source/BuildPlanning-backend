package com.acme.backendbuildplanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class BackendBuildPlanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendBuildPlanningApplication.class, args);

        openSwaggerUI();
    }

    private static void openSwaggerUI() {
        try {
            String swaggerUrl = "http://localhost:8081/swagger-ui/index.html";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(swaggerUrl));
            } else {
                System.out.println("Desktop no soportado. Abre Swagger manualmente: " + swaggerUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
