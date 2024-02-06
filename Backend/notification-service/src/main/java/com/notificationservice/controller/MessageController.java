package com.notificationservice.controller;

import com.notificationservice.request.MessageRequest;
import com.notificationservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@RequestBody MessageRequest messageRequest){
        return messageService.sentMessage(messageRequest);
    }
}
