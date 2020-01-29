package com.example.demo;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MyProducerGateway {

    @Gateway(requestChannel = "out.event")
    void sendEvent(User user);

}
