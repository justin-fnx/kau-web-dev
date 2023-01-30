package com.example.kaushop.service;

import com.example.kaushop.model.StockMessageDto;
import com.example.kaushop.model.TransactionPhase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceiver {
    private final ProductService productService;
    private final MessageSender messageSender;
    private final ObjectMapper mapper;

    @SqsListener(value = "200212206-stock-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS )
    public void receiveStringMessage(@Payload String payload,
                                     @Header("tid") String tid,
                                     @Header("phase") String phase) throws Exception {

        if(phase.equals(TransactionPhase.TRY.getType())) {
            StockMessageDto stockMessage = mapper.readValue(payload, StockMessageDto.class);
            boolean changeResult = productService.addStock(stockMessage.getProductId(), stockMessage.getStockChanged());

            try {
                if (!changeResult) {
                    messageSender.sendAck(tid, TransactionPhase.CANCEL);
                } else {
                    messageSender.sendAck(tid, TransactionPhase.CONFIRM);
                }
            } catch(Exception e) {
                // 메시지 처리 실패시 재고 원복
                productService.addStock(stockMessage.getProductId(), stockMessage.getStockChanged() * -1);
            }
        } else {

        }
    }
}