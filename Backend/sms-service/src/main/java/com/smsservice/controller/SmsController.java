package com.smsservice.controller;

import com.smsservice.request.SmsRequest;
import com.smsservice.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;

    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public String sentMessage(@RequestBody SmsRequest smsRequest){
        return smsService.sentMessage(smsRequest);
    }
}
