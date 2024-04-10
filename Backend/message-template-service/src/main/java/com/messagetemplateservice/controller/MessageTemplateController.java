package com.messagetemplateservice.controller;

import com.messagetemplateservice.request.MessageTemplateRequest;
import com.messagetemplateservice.response.MessageTemplateResponse;
import com.messagetemplateservice.service.MessageTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
