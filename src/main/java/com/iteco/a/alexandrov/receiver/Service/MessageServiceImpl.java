package com.iteco.a.alexandrov.receiver.Service;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import com.iteco.a.alexandrov.receiver.Repository.MessageRepository;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Interaction service with RabbitMQ queue and H2 database.
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Saves or updates entries in the H2 database.
     *
     * @param messageEntity - The essence of the message that is stored in the database.
     * @return - Saved message object.
     */
    public MessageEntity saveOrUpdate(MessageEntity messageEntity) {
        return messageRepository.save(messageEntity);
    }

    /**
     * The method listens for the message queue and, if there are messages, subtracts them from the queue.
     *
     * @param message - Received message from the queue.
     * @param channel - The channel on which the message was received.
     * @throws Exception -In case of errors when working with the queue.
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String textBody = new String(message.getBody());
        log.info("Consuming Message - " + textBody);

        MessageEntity msgEnt = new MessageEntity(textBody);
        saveOrUpdate(msgEnt);
        if (checkMessageSavedToH2(msgEnt)) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("Message save to H2");
        } else {
            log.error("Message don't save to H2!");
        }
    }

    /**
     * Checks whether the received message is correctly written to the H2 database.
     * @param msgEnt - The message that will be searched in the database.
     * @return - Search Result.
     */
    private boolean checkMessageSavedToH2(MessageEntity msgEnt) {
        return msgEnt.getId() == (messageRepository.getById(msgEnt.getId()).map(MessageEntity::getId).orElse(-1L));
    }
}