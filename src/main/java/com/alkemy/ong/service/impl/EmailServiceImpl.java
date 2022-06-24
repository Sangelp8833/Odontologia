package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.EmailException;
import com.alkemy.ong.service.IEmailService;
import com.alkemy.ong.util.EmailMessages;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;

@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${app.sendgrid.sender}")
    private String sender;

    @Value("${app.sendgrid.apiKey}")
    private String apiKey;

    @Value("${app.sendgrid.templateIdRegister}")
    private String templateIdRegister;

    private static final Logger logger = LoggerFactory.getLogger("EmailLog");

    public void send(Mail mail) throws Exception {

        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            if (response.getStatusCode() != HttpStatus.ACCEPTED.value()) {
                logger.info(response.getBody());
                throw new EmailException();
            }

        } catch (EmailException ex) {
            logger.info(EmailMessages.ERROR_TEXT);
        }
    }

    @Override
    public void sendRegisterConfirmation(String receiverEmail, String name) throws Exception {

        Email from = new Email(sender);
        Email to = new Email(receiverEmail);

        Personalization personalization = new Personalization();
        personalization.addTo(to);
        personalization.setSubject(EmailMessages.WELCOME_TEXT);
        personalization.addDynamicTemplateData("name", name);

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.addPersonalization(personalization);
        mail.setTemplateId(templateIdRegister);

        send(mail);

    }

    @Override
    public void sendText(String receiverEmail, String subject, String body) throws Exception {

        Email from = new Email(sender);
        Email to = new Email(receiverEmail);
        Content content = new Content("text/plain", body);

        Mail mail = new Mail(from, subject, to, content);

        send(mail);

    }

}