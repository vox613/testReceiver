package com.iteco.a.alexandrov.receiver.Entity;

import javax.persistence.*;

/**
 * A class is a message entity that is mapped to a database table.
 */
@Entity
@Table(name = "messages_table")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String message;

    public MessageEntity() {
    }

    public MessageEntity(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public MessageEntity(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "id : " + id +
                ", message='" + message + '\'' +
                '}';
    }
}