package com.kleuton.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI springApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Restful Projeto FullStak")
                        .description("Documentação do Projeto Integrado.")
                        .version("1.1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Kleuto.dev").url("https://www.kleuton.dev")))
                .externalDocs(new ExternalDocumentation()
                        .description("Link do Repositório da Aplicação")
                        .url("https://github.com/kleutons/projeto-fullstack/tree/main/backend"));
    }

}
