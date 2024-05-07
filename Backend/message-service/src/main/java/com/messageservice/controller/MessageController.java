package com.messageservice.controller;

import com.messageservice.entity.Message;
import com.messageservice.request.MessageRequest;
import com.messageservice.response.MessageResponse;
import com.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse createMessage(@RequestBody MessageRequest messageRequest, @RequestHeader("Authorization") String authorizationHeader){
        return messageService.createMessage(messageRequest, authorizationHeader);
    }

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    public HashMap<String, List<Message>> getAllMessage(@RequestHeader("Authorization") String authorizationHeader){
        return messageService.getAllMessage(authorizationHeader);
    }

    @GetMapping("get/{name}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse getMessage(@PathVariable String name, @RequestHeader("Authorization") String authorizationHeader){
        return messageService.getMessage(name, authorizationHeader);
    }

    @PutMapping("update/{name}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse updateMessage(@PathVariable String name, @RequestBody MessageRequest messageRequest, @RequestHeader("Authorization") String authorizationHeader){
        return messageService.updateMessage(name, messageRequest, authorizationHeader);
    }

    @DeleteMapping("delete/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteMessage(@PathVariable String name, @RequestHeader("Authorization") String authorizationHeader){
        return messageService.deleteMessage(name, authorizationHeader);
    }

    @PostMapping("sent/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@PathVariable String name, @RequestHeader("Authorization") String authorizationHeader){
        return messageService.sentMessage(name, authorizationHeader);
    }
}
