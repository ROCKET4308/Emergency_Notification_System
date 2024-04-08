package com.mailservice.service;


import com.mailservice.request.MessageRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${send_grid.api_key}")
    private String SEND_GRID_API_KEY;

    public Map<String, String> sentMessage(MessageRequest messageRequest) throws IOException {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        List<String> contacts = messageRequest.getRecipientContacts();
        for(String contact : contacts){
            String status;
            if(isValidEmail(contact)){
                status = sentMailMessage(messageRequest.getMessageText(), contact);
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
            deliveryStatusMap.put(contact, status);
        }
        return deliveryStatusMap;
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

        String status;
        if(messageId != null && messageId.length() == 22){
            status = "Delivered";
        }else{
            status = "Not Delivered";
        }
        return status;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
