package com.abn.airline.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {
    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("AIRLINE SEARCH API")
                .version("1.0")
                .description("This API exposes endpoints to manage Airline Operations.");
        return new OpenAPI().info(info);
    }
}