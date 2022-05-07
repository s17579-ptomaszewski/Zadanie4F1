package com.example.zadanie4f1.producer;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class QueueProducer {
    private final JmsTemplate jmsTemplate;
//    @Scheduled(fixedRate = 2000)
    public void sendHello() {
        HelloMessage helloMessage = HelloMessage.builder()
                .id(HelloMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message("Hello world!")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.QUEUE_HELLO_WORLD, helloMessage);
        System.out.println("HelloWorldQueueProducer.sendHello - sent message: "+ helloMessage);
    }
}

