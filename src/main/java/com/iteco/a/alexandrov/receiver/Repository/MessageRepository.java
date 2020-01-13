package com.iteco.a.alexandrov.receiver.Repository;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository implements the functionality of the JPA repository. Provides interoperability with H2 database.
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    Optional<MessageEntity> getById(long id);
}