package com.notificationservice.service;

import com.notificationservice.request.MassageRequest;
import com.notificationservice.twilio.PhoneMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final PhoneMassage phoneMassage;
    public String sendEmail(MassageRequest massageRequest) {
        try {
            for(String phoneNumber : massageRequest.getContacts()){
                phoneMassage.sentMassage(massageRequest.getMassageText(), phoneNumber);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "Massage sent successfully";
    }
}
