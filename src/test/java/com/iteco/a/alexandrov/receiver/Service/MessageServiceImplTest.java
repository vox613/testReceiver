package com.iteco.a.alexandrov.receiver.Service;

import com.iteco.a.alexandrov.receiver.Entity.MessageEntity;
import com.iteco.a.alexandrov.receiver.Repository.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageServiceImpl messageService;


    @Test
    public void saveOrUpdateTest() {
        long id = 1L;
        String message = "testMsg";
        MessageEntity messageToDB = new MessageEntity(id, message);

        Mockito.when(messageRepository.save(any(MessageEntity.class))).thenReturn(new MessageEntity(id, message));

        MessageEntity savedMessage = messageService.saveOrUpdate(messageToDB);

        assertThat(savedMessage.getMessage()).isEqualTo(message);
        assertThat(savedMessage.getId()).isEqualTo(id);
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }

}