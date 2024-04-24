package com.messageservice.service;

import com.messageservice.entity.Message;
import com.messageservice.repository.MessageRepository;
import com.messageservice.request.MessageRequest;
import com.messageservice.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final WebClient.Builder webClientBuilder;


    public MessageResponse createMessage(MessageRequest messageRequest, String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        if(messageRepository.findByEmailAndName(email, messageRequest.getName()).isPresent()){
            throw new IllegalArgumentException("Message with that name " + messageRequest.getName() + " is presented, change name");
        }
        for(String recipientContact : messageRequest.getRecipientContacts()){
            Message message = Message.builder().email(email).name(messageRequest.getName())
                    .recipientContact(recipientContact).messageText(messageRequest.getMessageText()).build();
            messageRepository.save(message);
        }

        return MessageResponse.builder().name(messageRequest.getName()).messageText(messageRequest.getMessageText())
                .recipientContacts(messageRequest.getRecipientContacts()).build();
    }

    public MessageResponse getMessage(String name, String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        List<Message> messageList = messageRepository.findAllByEmailAndName(email, name);
        if(messageList.isEmpty()){
            throw new IllegalArgumentException("There is no message with that name" + name);
        }

        List<String> recipientContacts = new ArrayList<>();
        for(Message message : messageList){
            recipientContacts.add(message.getRecipientContact());
        }

        return MessageResponse.builder().name(messageList.get(0).getName())
                .messageText(messageList.get(0).getMessageText()).recipientContacts(recipientContacts).build();
    }

    public MessageResponse updateMessage(String name, MessageRequest messageRequest, String authorizationHeader) {
       deleteMessage(name, authorizationHeader);
       return createMessage(messageRequest, authorizationHeader);
    }


    public String deleteMessage(String name, String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        if(messageRepository.findByEmailAndName(email, name).isEmpty()){
            throw new IllegalArgumentException("Error to find message with name "+ name);
        }
        List<Message> messageDeletedList = messageRepository.deleteAllByEmailAndName(email, name);
        if(!messageDeletedList.isEmpty()){
            return "Successfully deleted message: " + name;
        }else{
            return "Cause some error";
        }
    }


    public HashMap<String, List<Message>> getAllMessage(String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        List<Message> messageList = messageRepository.findAllByEmail(email);
        if(messageList.isEmpty()){
            throw new IllegalArgumentException("Error to find message for this user");
        }
        HashMap<String, List<Message>> messageMap = new HashMap<>();
        for (Message message : messageList){
            String messageName  = message.getName();
            if(messageMap.containsKey(messageName)){
                messageMap.get(messageName).add(message);
            } else {
                messageMap.put(messageName, new ArrayList<>(Arrays.asList(message)));
            }
        }
        return messageMap;
    }

    //TODO: sent by api request to notification-service
    public Map<String, String> sentMessage(String name, String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        List<Message> messageList = messageRepository.findAllByEmailAndName(email, name);
        if(messageList.isEmpty()){
            throw new IllegalArgumentException("Error to find message with name "+ name);
        }
        Message firstMessage = messageList.get(0);
        MessageRequest messageRequest = MessageRequest.builder()
                .name(firstMessage.getName())
                .messageText(firstMessage.getMessageText())
                .recipientContacts(messageList.stream()
                        .map(Message::getRecipientContact)
                        .collect(Collectors.toList()))
                .build();

        return webClientBuilder.build()
                .post()
                .uri("http://notification-service/notification/sent")
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .body(Mono.just(messageRequest), MessageRequest.class)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public String getEmailByToken(String authorizationHeader){
        return webClientBuilder.build().get()
                .uri("http://auth-service/auth/extractEmail")
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
