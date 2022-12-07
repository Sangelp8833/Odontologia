package com.sangelp.dh.domain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfig {

    private static final String TITLE = "Reservación de turnos - odontología.";
    private static final String DESCRIPTION = "Explora cada unos de los endpoints que se encuentran al interior " +
            "del micro servicio para reservar turnos en un centro odontológico.";
    private static final String VERSION = "0.0.1-SNAPSHOT";

    @Bean
    public GroupedOpenApi api(){
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI(){
        return new OpenAPI().info(new Info()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION));
    }

}
