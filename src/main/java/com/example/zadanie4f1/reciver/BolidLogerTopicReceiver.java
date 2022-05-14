package com.example.zadanie4f1.reciver;

import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.BolidStatistic;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
public class BolidLogerTopicReceiver {
    @JmsListener(destination = JmsConfig.TOPIC_HELLO_WORLD, containerFactory =
            "topicConnectionFactory")
    public void receiveLogData(@Payload BolidStatistic convertedBolidStatistic,
                               @Headers MessageHeaders messageHeaders,
                               BolidStatistic bolidStatistic) {
        System.out.println("Bolid LOG: " + "\n" + "Time: " + convertedBolidStatistic.getCreatedAt() + "\n"
        + "Engine Temperature: " + convertedBolidStatistic.getEngineTemperature() + "\n"
        + "Tire Pressure: " + "\n" + "Front Left: " + convertedBolidStatistic.getTirePressureFrontLeft()
        + "\n" + "Front Right: " + convertedBolidStatistic.getTirePressureFrontRight()
        + "\n" + "Rear Left: " + convertedBolidStatistic.getTirePressureRearLeft() + "\n"
        + "Rear Right: " + convertedBolidStatistic.getTirePressureRearRight() + "\n"
        + "Oil Pressure: " + convertedBolidStatistic.getOilPressure() + "\n"
        + "Fuel Level: " + convertedBolidStatistic.getFuelLevel());


    }
}
