package com.notificationservice.service;

import com.notificationservice.entity.MassageStatus;
import com.notificationservice.repository.MassageStatusRepository;
import com.notificationservice.request.MassageRequest;
import com.notificationservice.twilio.Massager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final Massager massager;
    private final MassageStatusRepository massageStatusRepository;

    //TODO: Make function to automatically detect is mail or phone massage


    public Map<String, String> sentPhoneMassage(MassageRequest massageRequest) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        try {
            for(String phoneNumber : massageRequest.getContacts()){
                String messageId =  massager.sentPhoneMassage(massageRequest.getMassageText(), phoneNumber);
                MassageStatus massage = new MassageStatus();
                if(messageId.length() == 34){
                     massage.setMessageText(massageRequest.getMassageText());
                     massage.setRecipientContact(phoneNumber);
                     massage.setStatus("Delivered");
                     massage.setSentMassageId(messageId);
                     deliveryStatusMap.put(phoneNumber, "Delivered");
                }else{
                    massage.setMessageText(massageRequest.getMassageText());
                    massage.setRecipientContact(phoneNumber);
                    massage.setStatus("Not Delivered");
                    deliveryStatusMap.put(phoneNumber, "Not Delivered");
                }
                massageStatusRepository.save(massage);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return deliveryStatusMap;
    }

    public Map<String, String> sentMailMassage(MassageRequest massageRequest) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        try {
            for(String email : massageRequest.getContacts()){
                String messageId = massager.sentMailMassage(massageRequest.getMassageText(), email);
                MassageStatus massage = new MassageStatus();
                if(messageId.length() == 22){
                    massage.setMessageText(massageRequest.getMassageText());
                    massage.setRecipientContact(email);
                    massage.setStatus("Delivered");
                    massage.setSentMassageId(messageId);
                    deliveryStatusMap.put(email, "Delivered");
                }else{
                    massage.setMessageText(massageRequest.getMassageText());
                    massage.setRecipientContact(email);
                    massage.setStatus("Not Delivered");
                    deliveryStatusMap.put(email, "Not Delivered");
                }
                massageStatusRepository.save(massage);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return deliveryStatusMap;
    }
}
