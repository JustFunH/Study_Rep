package com.meguru.decorator.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class MyController {

    @PostMapping
    public Map<String, Object> origin(@TimestampRequestBody Map<String, Object> json) {
        return json;
    }
}
