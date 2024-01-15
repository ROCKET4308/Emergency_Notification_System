package com.notificationservice.service;

import com.notificationservice.entity.Mailing;
import com.notificationservice.entity.Massage;
import com.notificationservice.repository.MailingRepository;
import com.notificationservice.repository.MassageRepository;
import com.notificationservice.request.MassageRequest;
import com.notificationservice.twilio.Massager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final Massager massager;
    private final MailingRepository mailingRepository;
    private final MassageRepository massageRepository;

    public String sentPhoneMassage(MassageRequest massageRequest) {
        try {
            Mailing mailing = new Mailing(massageRequest.getMassageText());
            mailingRepository.save(mailing);
            for(String phoneNumber : massageRequest.getContacts()){
                String messageId =  massager.sentPhoneMassage(massageRequest.getMassageText(), phoneNumber);
                Massage massage = new Massage();
                if(messageId.length() == 34){
                     massage.setMailing(mailing);
                     massage.setUserContact(phoneNumber);
                     massage.setStatus("Delivered");
                     massage.setSentMassageId(messageId);
                }else{
                    massage.setMailing(mailing);
                    massage.setUserContact(phoneNumber);
                    massage.setStatus("Not Delivered");
                }
                massageRepository.save(massage);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        //TODO: return list of number with there delivery status
        return "Massage sent";
    }

    public String sentMailMassage(MassageRequest massageRequest) {
        try {
            Mailing mailing = new Mailing(massageRequest.getMassageText());
            mailingRepository.save(mailing);
            for(String email : massageRequest.getContacts()){
                String messageId = massager.sentMailMassage(massageRequest.getMassageText(), email);
                Massage massage = new Massage();
                if(messageId.length() == 22){
                    massage.setMailing(mailing);
                    massage.setUserContact(email);
                    massage.setStatus("Delivered");
                    massage.setSentMassageId(messageId);
                }else{
                    massage.setMailing(mailing);
                    massage.setUserContact(email);
                    massage.setStatus("Not Delivered");
                }
                massageRepository.save(massage);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        //TODO: return list of number with there delivery status
        return "Massage sent";
    }
}
