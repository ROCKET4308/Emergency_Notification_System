package com.notificationservice.controller;

import com.notificationservice.request.NotificationRequest;
import com.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    private final NotificationService messageService;

    //TODO: make api request to rebalance service
    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@RequestBody NotificationRequest notificationRequest){
        return messageService.sentMessage(notificationRequest);
    }
}
