package br.com.fiap.hackathon.doctor.configuration.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Authorization")
                        .version("v1")
                        .description("Authorization API")
                        .termsOfService("https://github.com/Tatu-Armadillo")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/Tatu-Armadillo")))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")));
    }

}
