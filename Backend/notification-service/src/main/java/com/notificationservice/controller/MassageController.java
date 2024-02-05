package com.notificationservice.controller;

import com.notificationservice.request.MassageRequest;
import com.notificationservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("massage")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MassageController {
    private final MessageService massageService;

    //TODO: change phone and mail controller into sent controller

    @PostMapping("phone")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentPhoneMassage(@RequestBody MassageRequest massageRequest){
        return massageService.sentPhoneMassage(massageRequest);
    }

    @PostMapping("mail")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMailMassage(@RequestBody MassageRequest massageRequest){
        return massageService.sentMailMassage(massageRequest);
    }
}
