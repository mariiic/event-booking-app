package ru.mariiic.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariiic.impl.model.OutboxNode;
import ru.mariiic.impl.model.OutboxStatus;
import ru.mariiic.impl.repository.OutboxRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OutboxService {
    private final OutboxRepository outboxRepository;

    @Transactional
    public List<OutboxNode> getBatch(int batchSize){
        return outboxRepository.getBatch(batchSize, OutboxStatus.PROCESSING.name(), List.of(OutboxStatus.NEW.name(),  OutboxStatus.FAILED.name()));
    }

    @Transactional
    public void updateStatus(List<OutboxNode> nodes, OutboxStatus newStatus) {
        outboxRepository.updateStatus(nodes, newStatus);
    }
}
