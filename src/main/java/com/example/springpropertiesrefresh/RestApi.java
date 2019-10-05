package com.example.springpropertiesrefresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApi {

    @Value("${foo}")
    int fooValue;

    @Autowired
    Environment env;

    @EventListener
    public void handleContextRefresh(PropertiesRefreshedEvent event) {
        var test = env.getProperty("foo");

        fooValue = Integer.valueOf(test);
    }

    @GetMapping("/foo")
    public int getValue() {
        return fooValue;
    }
}
