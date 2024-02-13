package com.notificationservice.controller;

import com.notificationservice.request.MessageTemplateRequest;
import com.notificationservice.response.MessageTemplateResponse;
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
    public MessageTemplateResponse createMessageTemplate(@RequestBody MessageTemplateRequest messageTemplateRequest, @RequestHeader("Authorization") String authorizationHeader){
        return messageTemplateService.createMessageTemplate(messageTemplateRequest, authorizationHeader);
    }

    @GetMapping("get/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public MessageTemplateResponse getMessageTemplate(@PathVariable String templateName, @RequestHeader("Authorization") String authorizationHeader){
        return messageTemplateService.getMessageTemplate(templateName, authorizationHeader);
    }

    @PutMapping("update/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public MessageTemplateResponse updateMessageTemplate(@PathVariable String templateName, @RequestBody MessageTemplateRequest messageTemplateRequest, @RequestHeader("Authorization") String authorizationHeader){
        return messageTemplateService.updateMessageTemplate(templateName,messageTemplateRequest, authorizationHeader);
    }

    @DeleteMapping("delete/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteMessageTemplate(@PathVariable String templateName, @RequestHeader("Authorization") String authorizationHeader){
        return messageTemplateService.deleteMessageTemplate(templateName, authorizationHeader);
    }

    @PostMapping("sent/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@PathVariable String templateName, @RequestHeader("Authorization") String authorizationHeader){
        return messageTemplateService.sentMessage(templateName, authorizationHeader);
    }
}
