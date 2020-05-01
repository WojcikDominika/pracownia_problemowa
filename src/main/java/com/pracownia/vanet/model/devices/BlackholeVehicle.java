package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.road.Road;

import java.util.Collection;
import java.util.List;

public class BlackholeVehicle extends Vehicle {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    public BlackholeVehicle(Road road, int id, double range, double speed, Collection<Device> devices) {
        super(road, id, range, speed);
        tasks.add(new Task(devices.stream().findFirst().get(), "Sieema", 1));
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        if (receivedFrom == this ? !event.getRoutingPath().contains("->") : false) {
            getTasks().forEach(task -> task.done = true);
        }
        System.out.println("Message ID " + event.getId() + " blocked by 8l4cKh0l3 ID " + getId());
        event.setId(-1);
        return event;
    }
}
