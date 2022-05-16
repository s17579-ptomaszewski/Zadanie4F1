package com.example.zadanie4f1.reciver;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.BolidStatistic;
import com.example.zadanie4f1.model.DescendMessage;
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
public class SendAndReceiveReceiverPitStop {
    private final JmsTemplate jmsTemplate;
    @JmsListener(destination = JmsConfig.QUEUE_SEND_AND_RECEIVE)
    public void receiveAndRespond(@Payload DescendMessage convertedMessage,
                                  @Headers MessageHeaders headers,
                                  Message message) throws JMSException {
        System.out.println("SendAndReceiveReceiver.receiveAndRespond message: "+convertedMessage);
                Destination replyTo = message.getJMSReplyTo();
        DescendMessage msg = DescendMessage.builder()
                .id(BolidStatistic.nextId())
                .createdAt(LocalDateTime.now())
                .message("OK")
                .build();
        jmsTemplate.convertAndSend(replyTo, msg);
    }
}
