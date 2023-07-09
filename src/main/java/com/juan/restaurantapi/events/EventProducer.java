package com.juan.restaurantapi.events;

import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {
    private final AmqpTemplate amqpTemplate;
    private final MessageConverter messageConverter;

    @Autowired
    public EventProducer(AmqpTemplate amqpTemplate, MessageConverter messageConverter) {
        this.amqpTemplate = amqpTemplate;
        this.messageConverter = messageConverter;
    }

    public void publishEvent(OrderDTO event) {
        Message message = messageConverter.toMessage(event, null);
        amqpTemplate.convertAndSend("test-exchange", "", message);
    }
}
