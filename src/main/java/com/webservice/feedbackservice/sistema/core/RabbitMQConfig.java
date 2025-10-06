package com.webservice.feedbackservice.sistema.core;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String queue1_name = "feedback-create.queue";
    private static final String queue2_name = "feedback.created.email.queue";
    @Bean
    public Queue queue1(){
        return new Queue(queue1_name, true);
    }
    @Bean
    public Queue queue2(){
        return new Queue(queue2_name, true);
    }
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
