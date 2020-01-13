package com.iteco.a.alexandrov.receiver.Service;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

public interface MessageService extends ChannelAwareMessageListener {
    MessageEntity saveOrUpdate(MessageEntity messageEntity);
}
