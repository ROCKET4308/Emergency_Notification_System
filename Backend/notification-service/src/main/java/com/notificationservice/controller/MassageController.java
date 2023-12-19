package com.notificationservice.controller;

import com.notificationservice.request.MassageRequest;
import com.notificationservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("massage")
@RequiredArgsConstructor
public class MassageController {
    private final MessageService massageService;

    @PostMapping("email")
    @ResponseStatus(HttpStatus.OK)
    public String sendEmail(@RequestBody MassageRequest massageRequest){
        return massageService.sendEmail(massageRequest);
    }
}
