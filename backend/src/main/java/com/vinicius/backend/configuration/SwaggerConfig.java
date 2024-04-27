package com.vinicius.backend.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Prontuário PET API")
                                          .description("API para gerenciamento de animais e tutores")
                                          .version("v1")
                                          .license(new License().name("Apache 2.0")
                                                           .url("https://springdoc.org"))
                                          .contact(new Contact().email("viniciuscaetano.dev@gmail.com")
                                                           .name("Vinicius Caetano")));
    }
}
