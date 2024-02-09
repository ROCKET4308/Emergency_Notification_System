package com.notificationservice.controller;

import com.notificationservice.entity.MessageTemplate;
import com.notificationservice.request.MessageTemplateRequest;
import com.notificationservice.service.MessageTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("messageTemplate")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MessageTemplateController {
    private final MessageTemplateService messageTemplateService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public String createMessageTemplate(@RequestBody MessageTemplateRequest messageTemplateRequest){
        return messageTemplateService.createMessageTemplate(messageTemplateRequest);
    }

    @GetMapping("get/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public MessageTemplate getMessageTemplate(@PathVariable String templateName){
        return messageTemplateService.getMessageTemplate(templateName);
    }

    @PutMapping("update/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public MessageTemplate updeteMessageTemplate(@PathVariable String templateName){
        return messageTemplateService.updeteMessageTemplate(templateName);
    }

    @DeleteMapping("delete/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteMessageTemplate(@PathVariable String templateName){
        return messageTemplateService.deleteMessageTemplate(templateName);
    }

    @PostMapping("sent/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@PathVariable String templateName, @RequestHeader("Authorization") String authorizationHeader){
        return messageTemplateService.sentMessage(templateName);
    }
}
