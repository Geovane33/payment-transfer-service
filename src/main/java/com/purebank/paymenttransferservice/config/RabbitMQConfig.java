package com.purebank.paymenttransferservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queuePerformTransfer() {
        return QueueBuilder
                .durable("perform-transfer")
                .build();
    }

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder
                .directExchange("direct-exchange-default")
                .build();
    }

    @Bean
    public Binding bindingUpdateAccountBalance() {
        return BindingBuilder
                .bind(queuePerformTransfer())
                .to(directExchange())
                .with("queue-perform-transfer-key")
                .noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter(new ObjectMapperConfig().objectMapper());
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}