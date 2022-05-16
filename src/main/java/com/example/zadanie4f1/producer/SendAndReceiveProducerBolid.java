package com.example.zadanie4f1.producer;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.DescendMessage;
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
public class SendAndReceiveProducerBolid {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    @Scheduled(fixedRate = 30000)
    public void sendAndReceive() throws JMSException, JsonProcessingException {
        DescendMessage descendMessage = DescendMessage.builder()
                .id(DescendMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message("I need a pit stop")
                .build();
        TextMessage responseMessage = (TextMessage) jmsTemplate.sendAndReceive(
                JmsConfig.QUEUE_SEND_AND_RECEIVE, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage plainMessage = session.createTextMessage();
                        try {
                            plainMessage.setText(objectMapper.writeValueAsString(descendMessage));
                            plainMessage.setStringProperty("_type",
                                    DescendMessage.class.getName());
                            return plainMessage;
                        } catch (JsonProcessingException e) {
                            throw new JMSException("conversion to json failed: " +
                                    e.getMessage());
                        }
                    }
                });
        String responseText = responseMessage.getText();
        DescendMessage responseConverted = objectMapper.readValue(responseText,
                DescendMessage.class);
        System.out.println("SendAndReceiveProducer.sendAndReceive got response: "
                +responseText+"\n\tconvertedMessage: "+responseConverted);
    }
}