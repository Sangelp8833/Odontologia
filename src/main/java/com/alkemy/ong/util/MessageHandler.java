package com.alkemy.ong.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:messages.properties")
public class MessageHandler {

    public final String userFound;
    public final String resourceNotFound;
    public final String categoryNotFound;

    @Autowired
    public MessageHandler(@Value("${user.found}") String userFound,
                          @Value("${category.notFound}") String categoryNotFound,
                          @Value ("${resource.notFound}") String resourceNotFound) {

        this.userFound = userFound;
        this.resourceNotFound= resourceNotFound;
        this.categoryNotFound= categoryNotFound;
    }


}
