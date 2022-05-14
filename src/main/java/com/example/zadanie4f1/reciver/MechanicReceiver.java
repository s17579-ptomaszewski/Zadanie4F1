package com.example.zadanie4f1.reciver;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.DamageMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
class MechanicReceiver {
    @JmsListener(destination = JmsConfig.QUEUE_MECHANIC, containerFactory =
            "queueConnectionFactory")
    public void receiveHelloMessage(@Payload DamageMessage convertedDamageMessage,
                                    @Headers MessageHeaders messageHeaders,
                                    DamageMessage damageMessage) {
        System.out.println("Mechanik odczytał info o usterce: "+ convertedDamageMessage);
    }
}

