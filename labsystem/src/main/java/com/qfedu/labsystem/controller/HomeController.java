package com.qfedu.labsystem.controller;

import com.qfedu.labsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/adminapi/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/card")
    public Map<String, Integer> getCardData() {
        return homeService.getCardData();
    }

    @GetMapping("/labbook")
    public Object getLabBookData() {
        return homeService.getLabBookData();
    }
}
