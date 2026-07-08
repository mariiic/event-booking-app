package ru.mariiic.impl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mariiic.impl.model.OutboxNode;
import ru.mariiic.impl.model.OutboxStatus;
import ru.mariiic.impl.publisher.OutboxDispatcher;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OutboxProcessor {
    @Value("${outbox.batch.size}")
    private int batchSize;

    private final OutboxService outboxService;
    private final OutboxDispatcher outboxDispatcher;

    public void process(){
        List<OutboxNode> nodes = outboxService.getBatch(batchSize);
        if (nodes.isEmpty()){
            return;
        }
        List<OutboxNode> published =  new ArrayList<>();
        List<OutboxNode> failed =  new ArrayList<>();
        for (OutboxNode node : nodes) {
            try {
                boolean isPublished = outboxDispatcher.publishEventByTypeNaming(node.getMessageType(), node.getMessagePayload());
                if (!isPublished){
                    failed.add(node);
                } else  {
                    published.add(node);
                }
            } catch (JsonProcessingException e) {
                failed.add(node);
            }
        }
        if (!failed.isEmpty()){
            outboxService.updateStatus(failed, OutboxStatus.FAILED);
        }
        if (!published.isEmpty()) {
            outboxService.updateStatus(published, OutboxStatus.PUBLISHED);
        }
    }
}
