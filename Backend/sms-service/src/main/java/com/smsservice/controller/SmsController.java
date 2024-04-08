package com.smsservice.controller;

import com.smsservice.request.MessageRequest;
import com.smsservice.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("sms")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SmsController {
    private final SmsService smsService;


    //TODO: check is verified token or not
    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@RequestBody MessageRequest messageRequest, @RequestHeader("Authorization") String authorizationHeader){
        return smsService.sentMessage(messageRequest);
    }
}
