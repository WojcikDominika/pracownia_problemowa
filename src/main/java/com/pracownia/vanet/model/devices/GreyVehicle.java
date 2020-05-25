package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.road.Road;

import java.util.Optional;
import java.util.Random;

public class GreyVehicle extends Vehicle {

    // procent "upuszczanych" pakietów
    private final double percent = 50;

    public GreyVehicle(Road road, int id, double range, double speed) {
        super(road, id, range, speed);
    }

    @Override
    public Optional<Event> transfer(Event event, Device receivedFrom) {

        Random generator = new Random();
        if(generator.nextInt(100) > percent)
            return super.transfer(event, receivedFrom);
        else {
            System.out.println("Message ID " + event.getId() +" , routing path: " +event.getRoutingPath() + " " +
                    "dropped by gR3yI-I013 ID " + getId());
            return Optional.empty();
        }
    }

}
