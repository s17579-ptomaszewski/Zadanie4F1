package com.example.zadanie4f1.reciver;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.HelloMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
public class TopicReceiver {
    @JmsListener(destination = JmsConfig.TOPIC_HELLO_WORLD, containerFactory =
            "topicConnectionFactory")
    public void receiveHelloMessage(@Payload HelloMessage convertedHelloMessage,
                                    @Headers MessageHeaders messageHeaders,
                                    HelloMessage helloMessage) {
        System.out.println("HelloWorldTopicReceiver1.receiveHelloMessage, message: "+ convertedHelloMessage);
    }
}
