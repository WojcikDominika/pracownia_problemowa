package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.road.Road;

/**
 * WormholeVehicle represents Wormhole attacking vehicles
 */
public class WormholeVehicle extends Vehicle {


    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    public WormholeVehicle(Road road, int id, double range, double speed) {
        super(road, id, range, speed);
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        event.setMessage("MESSAGE WAS HACKED!");
        event.setRoutingPath(event.getRoutingPath() + "->" + getId());
        return event;

    }

}
