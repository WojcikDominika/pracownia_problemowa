package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.road.Road;

public class BlackholeVehicle extends Vehicle {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    public BlackholeVehicle(Road road, int id, double range, double speed) {
        super(road, id, range, speed);
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        if (receivedFrom == this ? !event.getRoutingPath().contains("->") : false) {
            getTask().done = true;
        }
        System.out.println("Message ID " + event.getId() + " blocked by 8l4cKh0l3 ID " + this.getId());
        event.setId(-1);
        return event;
    }
}
