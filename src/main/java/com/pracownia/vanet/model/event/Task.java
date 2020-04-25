package com.pracownia.vanet.model.event;

import com.pracownia.vanet.model.devices.Device;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private Instant lastGenerated;
    private final int sendEverySeconds;
    private AtomicInteger counter = new AtomicInteger();
    private Device target;
    private String message;
    private String routingPath;
    public boolean done = false;

    public Task(Device target, String message, int sendEverySeconds) {
        this.target = target;
        this.message = message;
        this.sendEverySeconds = sendEverySeconds;
        this.lastGenerated = Instant.now();
        this.routingPath = "";
    }

    public boolean ifDone() {
        return this.done;
    }

    public Optional<Event> prepareEvent() {
        if (!this.done) {
            Instant now = Instant.now();
            if (Duration.between(lastGenerated, now).getSeconds() > sendEverySeconds) {
                lastGenerated = Instant.now();
                return Optional.of(new Event(counter.getAndIncrement(), target, new Date(), message, routingPath));
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}