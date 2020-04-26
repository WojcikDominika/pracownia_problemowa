package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.road.Road;

import java.util.Collection;
import java.util.Random;

public class GreyVehicle extends Vehicle {

    // procent "upuszczanych" pakietów
    private final double percent = 100;

    public GreyVehicle(Road road, int id, double range, double speed, Collection<Device> devices) {
        super(road, id, range, speed);
        registerTask(new Task(devices.stream().findFirst().get(), "Woolooloo", 2));
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {

        Random generator = new Random();
        if(generator.nextInt(100) > percent)
            return super.transfer(event, receivedFrom);
        else {
            if (receivedFrom == this && !event.getRoutingPath().contains("->")) {
                getTask().setDone(true);
            }
            System.out.println("Message ID " + event.getId() + " blocked by gR3yI-I013 ID " + getId());
            event.setId(-1);
            return event;
        }
    }

}
