package com.iteco.a.alexandrov.receiver.Repository;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
}