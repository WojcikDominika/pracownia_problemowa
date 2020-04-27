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
    AtomicInteger counter = new AtomicInteger();
    Device target;
    String message;
    String routingPath;
    boolean done = false;
        //przesylanie wydarzen eventow, wiec on wysyla to w sekwencji czasu
        //kazdy task ma poczatek i koniec
    public Task(Device target, String message, int sendEverySeconds) {
        this.target = target;
        this.message = message;
        this.sendEverySeconds = sendEverySeconds;
        this.lastGenerated = Instant.now();
        this.routingPath = "";
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
/*
    public Optional<Event> prepareEvent(){
        Instant now = Instant.now();
        if(Duration.between(lastGenerated, now).getSeconds() > sendEverySeconds){
            lastGenerated = Instant.now();
            return Optional.of(new Event(counter.getAndIncrement(), target, new Date(), message, routingPath));
        } else{
            return Optional.empty();
        }
    }*/

    //przygotowanie wiadomosci ktora bedzie przesylana do targeta,
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
