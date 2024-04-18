package com.notificationservice.controller;

import com.notificationservice.entity.NotificationStatus;
import com.notificationservice.request.NotificationRequest;
import com.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    private final NotificationService messageService;

    @PostMapping("sent")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@RequestBody NotificationRequest notificationRequest){
        List<NotificationStatus> notificationStatusList = messageService.sentMessage(notificationRequest);
        //TODO: make api request to rebalance service
        return new HashMap<>();
    }

    @PostMapping("retrySent")
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationStatus> retrySentMessage(@RequestBody NotificationRequest notificationRequest){
        List<NotificationStatus> notificationStatusList = messageService.sentMessage(notificationRequest);
        return notificationStatusList;
    }
}
