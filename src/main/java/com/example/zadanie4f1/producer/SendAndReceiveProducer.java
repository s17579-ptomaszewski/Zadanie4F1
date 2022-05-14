package com.example.zadanie4f1.producer;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.BolidStatistic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class SendAndReceiveProducer {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
//    @Scheduled(fixedRate = 2000)
    public void sendAndReceive() throws JMSException, JsonProcessingException {
        BolidStatistic bolidStatistic = BolidStatistic.builder()
                .id(BolidStatistic.nextId())
                .createdAt(LocalDateTime.now())
//                .message("Thank you")
                .build();
        TextMessage responseMessage = (TextMessage) jmsTemplate.sendAndReceive(
                JmsConfig.QUEUE_SEND_AND_RECEIVE, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage plainMessage = session.createTextMessage();
                        try {
                            plainMessage.setText(objectMapper.writeValueAsString(bolidStatistic));
                            plainMessage.setStringProperty("_type",
                                    BolidStatistic.class.getName());
                            return plainMessage;
                        } catch (JsonProcessingException e) {
                            throw new JMSException("conversion to json failed: " +
                                    e.getMessage());
                        }
                    }
                });
        String responseText = responseMessage.getText();
        BolidStatistic responseConverted = objectMapper.readValue(responseText,
                BolidStatistic.class);
        System.out.println("SendAndReceiveProducer.sendAndReceive got response: "
                +responseText+"\n\tconvertedMessage: "+responseConverted);
    }
}