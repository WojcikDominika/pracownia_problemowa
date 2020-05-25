package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.road.Road;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BlackholeVehicle extends Vehicle {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    public BlackholeVehicle(Road road, int id, double range, double speed, Collection<Device> devices) {
        super(road, id, range, speed);
        tasks.add(new Task(devices.stream().findFirst().get(), "I'm Blackhole", 1));
    }

    @Override
    public Optional<Event> transfer(Event event, Device receivedFrom) {
        if (receivedFrom == this ? !event.getRoutingPath().contains("->") : false) {
            getTasks().forEach(task -> task.done = true);
        }
        System.out.println("Message ID " + event.getId() + " blocked by Blackhole ID " + getId());
        event.setId(-1);
        return Optional.of(event);
    }
}
