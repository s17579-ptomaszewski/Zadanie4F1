package com.example.zadanie4f1.producer;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.DamageMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueProducerBolid {
    private final JmsTemplate jmsTemplate;

    public void sendDamageMessToBolid(DamageMessage damageMessage) {
        jmsTemplate.convertAndSend(JmsConfig.QUEUE_BOLID, damageMessage);
        System.out.println("Bolid - sent message: "+ damageMessage);
    }
}

