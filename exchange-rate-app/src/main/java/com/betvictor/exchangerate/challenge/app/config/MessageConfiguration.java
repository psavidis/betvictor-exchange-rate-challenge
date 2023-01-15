package com.betvictor.exchangerate.challenge.app.config;

import com.betvictor.exchangerate.challenge.app.common.condition.AsynApiEnabledCondition;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional(AsynApiEnabledCondition.class)
@EnableRabbit
@Configuration
public class MessageConfiguration {

    @Value("${queue.name}")
    private String message;

    @Bean
    public Queue queue() {
        return new Queue(message, true);
    }

}
