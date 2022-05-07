package com.example.zadanie4f1.reciver;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.HelloMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
class Receiver {
    @JmsListener(destination = JmsConfig.QUEUE_HELLO_WORLD, containerFactory =
            "queueConnectionFactory")
    public void receiveHelloMessage(@Payload HelloMessage convertedHelloMessage,
                                    @Headers MessageHeaders messageHeaders,
                                    HelloMessage helloMessage) {
        System.out.println("HelloWorldQueueReceiver1.receiveHelloMessage, message: "+ convertedHelloMessage);
    }
}

