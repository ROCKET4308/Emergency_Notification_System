package com.mailservice.controller;


import com.mailservice.request.MailRequest;
import com.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("mail")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {
    private final MailService mailService;

    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public String sentMessage(@RequestBody MailRequest mailRequest) throws IOException {
        return mailService.sentMessage(mailRequest);
    }
}
