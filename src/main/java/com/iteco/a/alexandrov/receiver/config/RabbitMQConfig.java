package com.iteco.a.alexandrov.receiver.config;

import com.iteco.a.alexandrov.receiver.Service.MessageService;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Queue Configurator Class.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue}")
    String queueName;

    @Value("${spring.rabbitmq.username}")
    String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    private MessageService messageServiceImpl;


    @Autowired
    public RabbitMQConfig(MessageService messageServiceImpl) {
        this.messageServiceImpl = messageServiceImpl;
    }

    /**
     * Creates a queue with specific parameters.
     *
     * @return - created queue.
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    /**
     * Method to configure message listener to use.
     *
     * @param connectionFactory - managed objects that allow the application to connect to the provider.
     * @return - Configured message listener to use.
     */
    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setMessageListener(messageServiceImpl);
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL); // change
        return simpleMessageListenerContainer;
    }


}
