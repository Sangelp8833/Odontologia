package com.sangelp.dh.domain.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    private static String url = "jdbc:postgresql://localhost:5432/odontologia";
    private static String username = "root"; // Poner su usuario
    private static String password = "sasa"; // colocar su contraseña

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }

}
