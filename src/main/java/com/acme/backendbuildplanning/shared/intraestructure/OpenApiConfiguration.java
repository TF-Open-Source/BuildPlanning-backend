package com.acme.backendbuildplanning.shared.intraestructure;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
        public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BuildPlanning API")
                        .description("API for managing BuildPlanning platform functionalities")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("BuildPlanning Wiki Documentation")
                        );
    }

}