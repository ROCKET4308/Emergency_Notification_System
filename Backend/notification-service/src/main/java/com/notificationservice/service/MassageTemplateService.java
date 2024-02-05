package com.notificationservice.service;

import com.notificationservice.entity.MassageTemplate;
import com.notificationservice.request.MassageTemplateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MassageTemplateService {

    //TODO:
    public String createMassageTemplate(MassageTemplateRequest massageTemplateRequest) {
        return "";
    }

    //TODO:
    public MassageTemplate getMassageTemplate(String templateName) {
        return new MassageTemplate();
    }

    //TODO:
    public MassageTemplate updeteMassageTemplate(String templateName) {
        return new MassageTemplate();
    }

    //TODO:
    public MassageTemplate deleteMassageTemplate(String templateName) {
        return new MassageTemplate();
    }

    //TODO: sent massage using massageService
    public Map<String, String> sentMassage(String templateName) {
        return new HashMap<>();
    }
}
