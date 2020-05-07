package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.road.Road;

import java.util.Optional;

public class WormholeVehicle extends Vehicle {


    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    public WormholeVehicle(Road road, int id, double range, double speed) {
        super(road, id, range, speed);
    }

    @Override
    public Optional<Event> transfer(Event event, Device receivedFrom) {
        event.setMessage("MESSAGE WAS HACKED!");
        event.setRoutingPath(event.getRoutingPath() + "->" + getId());
        return Optional.of(event);
    }

}

// kazdy chce wysla network szuka najkroteszej drogi do
// komunikacji transfer miedzy soba, send jako pierwszy
// ktprys 1 ma zadanie ze chce wysac do 5 wiec wysyla przez najkrotsza droga