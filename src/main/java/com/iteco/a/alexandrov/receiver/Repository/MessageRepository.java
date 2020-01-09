package com.iteco.a.alexandrov.receiver.Repository;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
    Optional<MessageEntity> getById(int id);
}