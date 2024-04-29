package com.fakemessageservice.controller;

import com.fakemessageservice.request.FakeMessageRequest;
import com.fakemessageservice.service.FakeMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fakeMessage")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FakeMessageController {
    private final FakeMessageService fakeMessageService;

    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public String sentMessage(@RequestBody FakeMessageRequest fakeMessageRequest){
        return fakeMessageService.sentMessage(fakeMessageRequest);
    }
}
