package com.example.springpropertiesrefresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class PropertiesFetcher {

    private static final String PROPERTY_SOURCE_NAME = "my-refreshable-properties";

    private ConfigurableEnvironment env;
    private ApplicationEventPublisher eventPublisher;

    int val = 2;

    @Autowired
    PropertiesFetcher(ConfigurableEnvironment env, ApplicationEventPublisher eventPublisher) {
        this.env = env;
        this.eventPublisher = eventPublisher;
        this.refresh();
    }

    @Scheduled(fixedRate = 1000)
    public void refresh() {

        var properties = getProperties();
        var propertySource = asPropertySource(properties);
        var event = new PropertiesRefreshedEvent(this);

        if (env.getPropertySources().get(PROPERTY_SOURCE_NAME) == null) {
            env.getPropertySources().addFirst(propertySource);
        } else {
            env.getPropertySources().replace(PROPERTY_SOURCE_NAME, propertySource);
        }

        eventPublisher.publishEvent(event);
    }

    private Map<String, Object> getProperties() {
        return Map.of("foo", val++);
    }

    private MapPropertySource asPropertySource(Map<String, Object> props) {
        return new MapPropertySource(PROPERTY_SOURCE_NAME, props);
    }
}
