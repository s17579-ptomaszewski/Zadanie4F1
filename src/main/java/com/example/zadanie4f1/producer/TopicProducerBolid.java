package com.example.zadanie4f1.producer;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.BolidStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TopicProducerBolid {
    private final JmsTemplate jmsTemplate;
    Random randomStat = new Random();

        @Scheduled(fixedRate = 15000)
    public void sendData() {
        BolidStatistic bolidStatistic = BolidStatistic.builder()
                .id(BolidStatistic.nextId())
                .createdAt(LocalDateTime.now())
                .engineTemperature(getRandomEngineTemp())
                .tirePressureFrontLeft(getRandomTirePressure())
                .tirePressureFrontRight(getRandomTirePressure())
                .tirePressureRearLeft(getRandomTirePressure())
                .tirePressureRearRight(getRandomTirePressure())
                .oilPressure(getRandomOilPressure())
                .fuelLevel(getRandomFuelLevel())
                .build();
        jmsTemplate.convertAndSend(JmsConfig.TOPIC_HELLO_WORLD, bolidStatistic);
        System.out.println("HelloWorldTopicProducer.sendHello - sent message: "+ bolidStatistic);
    }

    private double getRandomEngineTemp(){return 80 + (160 - 80) * randomStat.nextDouble();
    }
    private double getRandomTirePressure(){
        return 16 + (23 - 16) * randomStat.nextDouble();
    }

    private double getRandomOilPressure(){
        return 5 + (10 - 5) * randomStat.nextDouble();
    }

    private int getRandomFuelLevel(){
        return randomStat.nextInt(100);
    }
}

