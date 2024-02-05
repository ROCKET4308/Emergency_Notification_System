package com.notificationservice.controller;

import com.notificationservice.entity.MassageTemplate;
import com.notificationservice.request.MassageTemplateRequest;
import com.notificationservice.service.MassageTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("massageTemplate")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MassageTemplateController {
    private final MassageTemplateService massageTemplateService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public String createMassageTemplate(@RequestBody MassageTemplateRequest massageTemplateRequest){
        return massageTemplateService.createMassageTemplate(massageTemplateRequest);
    }

    @GetMapping("get/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public MassageTemplate getMassageTemplate(@PathVariable String templateName){
        return massageTemplateService.getMassageTemplate(templateName);
    }

    @PutMapping("update/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public MassageTemplate updeteMassageTemplate(@PathVariable String templateName){
        return massageTemplateService.updeteMassageTemplate(templateName);
    }

    @DeleteMapping("delete/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public MassageTemplate deleteMassageTemplate(@PathVariable String templateName){
        return massageTemplateService.deleteMassageTemplate(templateName);
    }

    @PostMapping("sent/{templateName}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> sentMassage(@PathVariable String templateName){
        return massageTemplateService.sentMassage(templateName);
    }
}
