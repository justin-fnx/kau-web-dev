package com.example.kaushop.service;

import com.example.kaushop.model.TransactionPhase;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {
    private static final String QUEUE_NAME = "2002122067-stock-result-queue";
    private final QueueMessagingTemplate messagingTemplate;

    public void sendAck(String tid, TransactionPhase phase) {
        Message msg = MessageBuilder.withPayload("ack")
                .setHeader("tid", tid)
                .setHeader("phase", phase.getType())
                .build();

        messagingTemplate.send(QUEUE_NAME, msg);
        log.info("Message ack sent - tid : {}, phase : {}", tid, phase);
    }
}
