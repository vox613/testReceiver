package com.iteco.a.alexandrov.receiver.Service;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import com.iteco.a.alexandrov.receiver.Repository.MessageRepository;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageService implements ChannelAwareMessageListener {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public void saveOrUpdate(MessageEntity messageEntity) {
        messageRepository.save(messageEntity);
    }


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String textBody = new String(message.getBody());
        MessageEntity msgEnt = new MessageEntity(textBody);
        saveOrUpdate(msgEnt);

        if (msgEnt.getId() == (messageRepository.getById(msgEnt.getId()).map(MessageEntity::getId).orElse(-1))) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
        System.out.println("getMessageProperties = " + message.getMessageProperties());
        System.out.println("Consuming Message - " + textBody);
    }

}