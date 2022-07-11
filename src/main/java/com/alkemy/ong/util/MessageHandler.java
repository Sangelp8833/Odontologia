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
    public final String activityNotFound;
    public final String newsNotFound;
    public final String categoryIdRequired;
    public final String slidesNotFound;
    public final String welcomeEmail;
    public final String errorEmail;
    public final String contactEmail;
    public final String usersNotFound;
    public final String slideNotFound;
    public final String slideIdRequired;
    public final String contactsNotFound;

    @Autowired
    public MessageHandler(
    		@Value("${user.found}") String userFound,
    		@Value("${news.notFound}")String newsNotFound,
    		@Value("${category-id.required}") String categoryIdRequired,
    		@Value("${category.notFound}") String categoryNotFound,
    		@Value ("${resource.notFound}") String resourceNotFound,
    		@Value("${slides.not-found}") String slidesNotFound,
        	@Value("${welcome.emailText}") String welcomeEmail,
        	@Value("${error.emailText}") String errorEmail,
        	@Value("${contact.emailText}") String contactEmail,
        	@Value("${category.notFound}") String activityNotFound,
    		@Value("${users.not-found}") String usersNotFound,
    		@Value("${slide.notFound}") String slideNotFound,
    		@Value("${slide-id.required}") String slideIdRequired,
    		@Value("${contacts.not-found}") String contactsNotFound) {

        this.userFound = userFound;
        this.resourceNotFound= resourceNotFound;
        this.categoryNotFound= categoryNotFound;
        this.activityNotFound = activityNotFound;
        this.newsNotFound = newsNotFound;
        this.categoryIdRequired = categoryIdRequired;
        this.slidesNotFound = slidesNotFound;
        this.welcomeEmail = welcomeEmail;
        this.errorEmail = errorEmail;
        this.contactEmail = contactEmail;
        this.usersNotFound = usersNotFound;
        this.slideNotFound = slideNotFound;
        this.slideIdRequired = slideIdRequired;
        this.contactsNotFound = contactsNotFound;

    }

}
