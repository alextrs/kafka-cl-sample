package com.example.demo;

import org.apache.avro.specific.SpecificData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import java.util.Map;

@Configuration
public class SpringConfig {

    @Bean
    public IntegrationFlow createProducer() {
        return IntegrationFlows
                .from("out.event")
                .channel("in.transfer.event")
                .get();
    }

    @Bean
    public IntegrationFlow createListenerForTopics() {
        // change the group id so we don't revoke the other partitions.
        return IntegrationFlows
                        .from("in.transfer.event")
                        // this is needed to convert GenericData$Record to correct type
                        .transform(record -> SpecificData.get().deepCopy(User.SCHEMA$, record))
                        .channel("in.event")
                        .get();
    }

}
