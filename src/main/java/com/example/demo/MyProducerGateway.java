package com.example.demo;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface MyProducerGateway {

    @Gateway(requestChannel = "out.event")
    void sendEvent(User user, @Header(KafkaHeaders.MESSAGE_KEY) String key);

}
