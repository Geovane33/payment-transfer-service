package com.purebank.paymenttransferservice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    @Autowired
    private Queue queueTransfer;

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder
                .directExchange("direct-exchange-default")
                .build();
    }

    @Bean
    public Binding bindingQueueTransfer() {
        return BindingBuilder
                .bind(queueTransfer)
                .to(directExchange())
                .with("queue-transfer-key")
                .noargs();
    }
}