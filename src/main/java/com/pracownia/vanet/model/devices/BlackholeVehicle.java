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
        event.setMessage("Message is not sent to connected vehicles!");
        event.setRoutingPath(event.getRoutingPath() + "->" + getId());
        return event;
    }
}
