package com.example.zadanie4f1.reciver;

import com.example.zadanie4f1.MessageRouter;
import com.example.zadanie4f1.config.JmsConfig;
import com.example.zadanie4f1.model.BolidStatistic;
import com.example.zadanie4f1.model.DamageMessage;
import com.example.zadanie4f1.producer.QueueProducerBolid;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BolidStateCheckerTopicReceiver {
    private final double engineTemperatureMax = 140;
    private final double tirePressureMin = 18;
    private final double oilPressureMax = 8;
    private final double fuelLevelMin = 15;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.TOPIC_HELLO_WORLD, containerFactory =
            "topicConnectionFactory")


    public void receiveBolidState(@Payload BolidStatistic convertedBolidStatistic,
                                  @Headers MessageHeaders messageHeaders,
                                  BolidStatistic bolidStatistic) {
        String errorMessage = "";
        boolean isCritical = false;
        boolean isError = false;

        if(convertedBolidStatistic.getFuelLevel() < fuelLevelMin){
            errorMessage += "low fuel lvl = " + convertedBolidStatistic.getFuelLevel();
            isError = true;
        }
        if(convertedBolidStatistic.getOilPressure() > oilPressureMax){
            errorMessage += "to high oil pressure  = " + convertedBolidStatistic.getOilPressure();
            isCritical = true;
            isError = true;

        }
        if(convertedBolidStatistic.getEngineTemperature() > engineTemperatureMax){
            errorMessage += "to high temperature engine  = " + convertedBolidStatistic.getEngineTemperature();
            isCritical = true;
            isError = true;

        }
        if(convertedBolidStatistic.getTirePressureRearRight() < tirePressureMin){
            errorMessage += "to low rear right tire pressure  = " + convertedBolidStatistic.getTirePressureRearRight();
            isError = true;
        }
        if(convertedBolidStatistic.getTirePressureRearLeft() < tirePressureMin){
            errorMessage += "to low rear left tire pressure  = " + convertedBolidStatistic.getTirePressureRearLeft();
            isError = true;
        }
        if(convertedBolidStatistic.getTirePressureFrontLeft() < tirePressureMin){
            errorMessage += "to low tire front left pressure  = " + convertedBolidStatistic.getTirePressureFrontLeft();
            isError = true;
        }
        if(convertedBolidStatistic.getTirePressureFrontRight() < tirePressureMin){
            errorMessage += "to low front right tire pressure  = " + convertedBolidStatistic.getTirePressureFrontRight();
            isError = true;
        }

        if(isError) {
            MessageRouter messageRouter = new MessageRouter(jmsTemplate);
            messageRouter.triggerMess(createDamageMessage(errorMessage, isCritical));
        }
    }

    private DamageMessage createDamageMessage(String mess, boolean isCritical){
        DamageMessage damageMessage = DamageMessage.builder()
                .id(DamageMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message(mess)
                .isCritical(isCritical)
                .build();
        return damageMessage;
    }
}

