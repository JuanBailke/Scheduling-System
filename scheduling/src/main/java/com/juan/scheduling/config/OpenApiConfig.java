package com.juan.scheduling.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Agendamento de Consultas")
                        .version("v1.0")
                        .description("API para gerenciamento de registros entre pacientes, médicos e agendamentos de consultas.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Juan Bailke")
                                .email("juan_bailke@hotmail.com")
                                .url("https://github.com/JuanBailke")
                                .url("https://www.linkedin.com/in/juan-felipe-cavalari-bailke"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
