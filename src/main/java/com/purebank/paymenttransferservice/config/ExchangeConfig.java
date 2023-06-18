package com.purebank.paymenttransferservice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    @Autowired
    private Queue queueUpdateAccountBalance;

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder
                .directExchange("direct-exchange-default")
                .build();
    }

    @Bean
    public Binding bindingQueueTransfer() {
        return BindingBuilder
                .bind(queueUpdateAccountBalance)
                .to(directExchange())
                .with("queue-update-accounts-balance-key")
                .noargs();
    }
}