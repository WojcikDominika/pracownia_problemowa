package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.CrossRoad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@NoArgsConstructor
public abstract class Device {

    /*------------------------ FIELDS REGION ------------------------*/
    protected int id;
    protected Point currentLocation = new Point();
    protected double range;
    protected AtomicInteger occurrences;

    /*------------------------ METHODS REGION ------------------------*/
    public Device(int id, Point currentLocation, double range) {
        occurrences = new AtomicInteger(0);
        this.id = id;
        this.currentLocation = currentLocation;
        this.range = range;
    }

    public abstract void move();
    public abstract void send(Network dynamicNetwork);
    public abstract Optional<Event> transfer(Event event, Device receivedFrom);
    public abstract void receive(Event event);
    public abstract void turn(CrossRoad crossRoad);
    public abstract void registerTask(Task task);
    public void incrementOccurrences() {
        this.occurrences.incrementAndGet();
    }
    public AtomicInteger getOccurrences() {
        return occurrences;
    }
}
    