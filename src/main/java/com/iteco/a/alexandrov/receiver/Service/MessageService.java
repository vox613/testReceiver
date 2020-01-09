package com.iteco.a.alexandrov.receiver.Service;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import com.iteco.a.alexandrov.receiver.Repository.MessageRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MessageService implements MessageListener {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageEntity> getAllMessages() {
        List<MessageEntity> messageEntities = new ArrayList<>();
        messageRepository.findAll().forEach(messageEntities::add);
        return messageEntities;
    }

    public MessageEntity getMessageById(int id) {
        return messageRepository.findById(id).orElse(new MessageEntity());
    }

    public void saveOrUpdate(MessageEntity messageEntity) {
        messageRepository.save(messageEntity);
    }

    public void delete(int id) {
        messageRepository.deleteById(id);
    }

    @Override
    public void onMessage(Message message) {
        String textBody = new String(message.getBody());
        System.out.println("getMessageProperties = " + message.getMessageProperties());
        System.out.println("Consuming Message - " + textBody);
        MessageEntity newMessage = new MessageEntity();
        newMessage.setMessage(textBody);
        saveOrUpdate(newMessage);
        System.out.println("msg id = " + newMessage.getId());
    }
}