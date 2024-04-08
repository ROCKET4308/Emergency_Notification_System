package com.mailservice.controller;


import com.mailservice.request.MessageRequest;
import com.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("mail")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {
    private final MailService smsService;


    //TODO: check is verified token or not
    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@RequestBody MessageRequest messageRequest, @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        return smsService.sentMessage(messageRequest);
    }
}
