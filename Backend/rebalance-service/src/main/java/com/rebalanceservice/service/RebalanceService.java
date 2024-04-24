package com.rebalanceservice.service;

import com.rebalanceservice.entity.NotificationStatus;
import com.rebalanceservice.repository.NotificationStatusRepository;
import com.rebalanceservice.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RebalanceService {
    //TODO: make repo to store message status
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
            }
        }

        if(!notDeliveredNotificationContactList.isEmpty()){
            NotificationRequest notificationRequest =
                    new NotificationRequest(notificationStatusList.get(0).getName(),
                            notificationStatusList.get(0).getMessageText(),
                            notDeliveredNotificationContactList
                    );
            //TODO: make api call to worker service retrySent controller and save message from return list to repository
        }

        for (NotificationStatus notification : notificationStatusList){
            notificationStatusMap.put(notification.getRecipientContact(), notification.getStatus());
        }
        return notificationStatusMap;
    }
}
