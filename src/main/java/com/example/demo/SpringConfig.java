package com.example.demo;

import org.apache.avro.specific.SpecificData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;

import java.util.Map;

@Configuration
@EnableKafka
public class SpringConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public DefaultKafkaHeaderMapper defaultMapper() {
        return new DefaultKafkaHeaderMapper();
    }

    @Bean
    public IntegrationFlow createProducer(ProducerFactory<String, String> producerFactory) {
        return IntegrationFlows
                .from("out.event")
                .handle(Kafka.outboundChannelAdapter(producerFactory)
                        .headerMapper(defaultMapper())
                        .topic("my-topic"))
                .get();
    }

    @Bean
    public IntegrationFlow createListenerForTopics() {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        // change the group id so we don't revoke the other partitions.
        return IntegrationFlows
                        .from(Kafka.messageDrivenChannelAdapter(new DefaultKafkaConsumerFactory(consumerProperties), "my-topic"))
                        // this is needed to convert GenericData$Record to correct type
                        .transform(record -> SpecificData.get().deepCopy(User.SCHEMA$, record))
                        .channel("in.event")
                        .get();
    }

}
