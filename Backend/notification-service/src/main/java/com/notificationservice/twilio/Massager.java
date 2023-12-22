package com.notificationservice.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import java.io.IOException;

@Component
public class Massager {

    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone_number}")
    private String PHONE_NUMBER;

    @Value("${send_grid.api_key}")
    private String SEND_GRID_API_KEY;

    public void sentPhoneMassage(String massageText, String recipientNumber){
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(recipientNumber),
                            new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                            massageText)
                    .create();
            System.out.println(message.getSid());
        }catch(Exception ex) { throw ex; }
    }

    public void sentMailMassage(String massageText, String recipientMail) throws IOException {
            Email from = new Email("mhaplanov@gmail.com");
            Email to = new Email(recipientMail);
            String subject = "Emergency Notification";
            Content content = new Content(
                    "text/plain",
                    massageText
            );

            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(SEND_GRID_API_KEY);
            Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("/mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) { throw ex; }
    }
}