package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.ConnectionRoute;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.CrossRoad;

import java.util.Optional;

public class CarAccident extends Device {


    /*------------------------ FIELDS REGION ------------------------*/
    private final static double TRUST_LEVEL_INCREASE = 0.1;
    private final static double TRUST_LEVEL_DECREASE = 0.4;

    /*------------------------ METHODS REGION ------------------------*/
    public CarAccident(int id, Point currentLocation, double range) {
        super(id, currentLocation, range);
    }

    @Override
    public void move() {
        //Does not move
    }

    @Override
    public void send(Network dynamicNetwork) {

    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        event.setRoutingPath(event.getRoutingPath() + "->" + "car_accident " + id);
        return event;
    }

    @Override
    public void receive(Event event) {
        // Does not receive
    }

    @Override
    public void turn(CrossRoad crossRoad) {
        // Does not
    }

    @Override
    public void registerTask(Task task) {

    }
}
