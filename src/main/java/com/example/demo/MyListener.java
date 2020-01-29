package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MyListener {

    @ServiceActivator(inputChannel = "in.event")
    public void handleRuleChangeEvent(@Payload User user) {
        System.out.println("Hello user" + user.getFirstName());
    }

}
