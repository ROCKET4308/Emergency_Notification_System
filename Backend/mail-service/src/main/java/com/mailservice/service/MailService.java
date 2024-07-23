package com.mailservice.service;


import com.mailservice.entity.Notification;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${send_grid.api_key}")
    private String SEND_GRID_API_KEY;

    public String sentMessage(Notification notification) throws IOException {
        return sentMailMessage(notification.getMessageText(), notification.getRecipientContact());
    }

    public String sentMailMessage(String messageText, String recipientMail) throws IOException {
        Email from = new Email("mhaplanov@gmail.com");
        Email to = new Email(recipientMail);
        String subject = "Emergency Notification";
        Content content = new Content(
                "text/plain",
                messageText
        );

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SEND_GRID_API_KEY);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("/mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        String messageId = response.getHeaders().get("X-Message-Id");
        System.out.println("Message ID: " + messageId);
        return messageId;
    }
}
