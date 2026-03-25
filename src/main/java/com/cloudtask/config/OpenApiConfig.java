package com.cloudtask.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("CloudTask API")
                .version("1.0.0")
                .description("Scalable Cloud-Native Task Management REST API")
                .contact(new Contact()
                        .name("Sai Kumar Moguluri")
                        .email("mogulurisaikumar@gmail.com")));
    }
}
