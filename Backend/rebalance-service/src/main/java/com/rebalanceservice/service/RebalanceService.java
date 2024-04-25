package com.rebalanceservice.service;

import com.rebalanceservice.entity.NotificationStatus;
import com.rebalanceservice.repository.NotificationStatusRepository;
import com.rebalanceservice.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RebalanceService {
    private final WebClient.Builder webClientBuilder;
    private final NotificationStatusRepository notificationStatusRepository;

    public Map<String, String> rebalance(List<NotificationStatus> notificationStatusList) {
        Map<String, String> notificationStatusMap = new HashMap<>();
        List<String> notDeliveredNotificationContactList = new ArrayList<>();

        for(NotificationStatus notification : notificationStatusList){
            if(notification.getStatus() != "Delivered"){
                notDeliveredNotificationContactList.add(notification.getRecipientContact());
                notificationStatusList.remove(notification);
            }else{
                notificationStatusRepository.save(notification);
                notificationStatusMap.put(notification.getRecipientContact(), notification.getStatus());
            }
        }

        if(!notDeliveredNotificationContactList.isEmpty()){
            NotificationRequest notificationRequest =
                    new NotificationRequest(notificationStatusList.get(0).getName(),
                            notificationStatusList.get(0).getMessageText(),
                            notDeliveredNotificationContactList
                    );
            List<NotificationStatus> notificationRetryStatusList= webClientBuilder.build()
                    .post()
                    .uri("http://notification-service/notification/retrySent")
                    .body(Mono.just(notificationRequest), NotificationRequest.class)
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
            for(NotificationStatus notification : notificationRetryStatusList){
                notificationStatusRepository.save(notification);
                notificationStatusMap.put(notification.getRecipientContact(), notification.getStatus());
            }
        }
        return notificationStatusMap;
    }
}
