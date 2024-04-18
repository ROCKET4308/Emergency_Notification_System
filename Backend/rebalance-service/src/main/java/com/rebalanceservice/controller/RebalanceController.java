package com.rebalanceservice.controller;

import com.rebalanceservice.entity.NotificationStatus;
import com.rebalanceservice.service.RebalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rebalance")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RebalanceController{
    private final RebalanceService rebalanceService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMessage(@RequestBody List<NotificationStatus> notificationStatusList){
        return rebalanceService.rebalance(notificationStatusList);
    }
}
