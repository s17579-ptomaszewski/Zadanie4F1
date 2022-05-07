package com.example.zadanie4f1.reciver;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class SendAndReceiveReceiver {
    private final JmsTemplate jmsTemplate;
    @JmsListener(destination = JmsConfig.QUEUE_SEND_AND_RECEIVE)
    public void receiveAndRespond(@Payload HelloMessage convertedMessage,
                                  @Headers MessageHeaders headers,
                                  Message message) throws JMSException {
        System.out.println("SendAndReceiveReceiver.receiveAndRespond message: "+convertedMessage);
                Destination replyTo = message.getJMSReplyTo();
        HelloMessage msg = HelloMessage.builder()
                .id(HelloMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message("You're welcome!")
                .build();
        jmsTemplate.convertAndSend(replyTo, msg);
    }
}
