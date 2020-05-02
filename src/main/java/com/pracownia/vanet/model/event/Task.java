package com.pracownia.vanet.model.event;

import com.pracownia.vanet.model.devices.Device;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Menage the transmission of events between devices at specified times
 */
public class Task {
    private Instant lastGenerated;
    private final int sendEverySeconds;
    AtomicInteger counter = new AtomicInteger();
    Device target;
    String message;

    public Task(Device target, String message, int sendEverySeconds) {
        this.target = target;
        this.message = message;
        this.sendEverySeconds = sendEverySeconds;
        this.lastGenerated = Instant.now();
    }

    public Optional<Event> prepareEventFor(Device sender){
        Instant now = Instant.now();
        if(Duration.between(lastGenerated, now).getSeconds() > sendEverySeconds){
            lastGenerated = Instant.now();
            return Optional.of(new Event(counter.getAndIncrement(), target, new Date(), message, String.valueOf(sender.getId())));
        } else{
            return Optional.empty();
        }
    }
}
