package com.example.springpropertiesrefresh;

import org.springframework.context.ApplicationEvent;

public class PropertiesRefreshedEvent extends ApplicationEvent {

    public PropertiesRefreshedEvent(Object source) {
        super(source);
    }

}
