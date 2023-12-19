package com.notificationservice.service;

import com.notificationservice.request.MassageRequest;
import com.notificationservice.twilio.PhoneMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final PhoneMassage phoneMassage;
    public String sendEmail(MassageRequest massageRequest) {
        try {
            phoneMassage.sentMassage();
        }catch (Exception e){
            System.out.println(e);
        }
        return "Massage sent successfully";
    }
}
