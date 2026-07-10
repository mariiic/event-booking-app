package ru.mariiic.impl.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mariiic.impl.service.OutboxProcessor;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {
    public final OutboxProcessor outboxProcessor;

    @Scheduled(fixedRateString = "${outbox.processing.fixedRate}")
    @Async
    public void process() {
        outboxProcessor.process();
    }
}
