package com.notificationservice.service;

import com.notificationservice.request.MassageRequest;
import com.notificationservice.twilio.Massager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final Massager massager;
    public String sentPhoneMassage(MassageRequest massageRequest) {
        try {
            for(String phoneNumber : massageRequest.getContacts()){
                massager.sentPhoneMassage(massageRequest.getMassageText(), phoneNumber);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "Massage sent successfully";
    }

    public String sentMailMassage(MassageRequest massageRequest) {
        try {
            for(String email : massageRequest.getContacts()){
                massager.sentMailMassage(massageRequest.getMassageText(), email);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "Massage sent successfully";
    }
}
