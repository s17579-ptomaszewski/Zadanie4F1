package com.example.zadanie4f1;

import com.example.zadanie4f1.model.DamageMessage;
import com.example.zadanie4f1.producer.QueueProducerBolid;
import com.example.zadanie4f1.producer.QueueProducerMechanic;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageRouter {
    private final JmsTemplate jmsTemplate;

    public void triggerMess(DamageMessage damageMessage){
        if(damageMessage.isCritical()){
            QueueProducerBolid queueProducerBolid = new QueueProducerBolid(jmsTemplate);
            QueueProducerMechanic queueProducerMechanic = new QueueProducerMechanic(jmsTemplate);

            queueProducerBolid.sendDamageMessToBolid(damageMessage);
            queueProducerMechanic.sendDamageMessToBolid(damageMessage);
        } else {
            QueueProducerBolid queueProducerBolid = new QueueProducerBolid(jmsTemplate);
            queueProducerBolid.sendDamageMessToBolid(damageMessage);
        }
    }
}
