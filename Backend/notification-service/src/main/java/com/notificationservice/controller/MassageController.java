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

    @PostMapping("phone")
    @ResponseStatus(HttpStatus.OK)
    public String sentPhoneMassage(@RequestBody MassageRequest massageRequest){
        return massageService.sentPhoneMassage(massageRequest);
    }

    @PostMapping("mail")
    @ResponseStatus(HttpStatus.OK)
    public String sentMailMassage(@RequestBody MassageRequest massageRequest){
        return massageService.sentMailMassage(massageRequest);
    }
}
