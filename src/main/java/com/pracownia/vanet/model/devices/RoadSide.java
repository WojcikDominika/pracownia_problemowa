package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.CrossRoad;

import java.util.Set;

public class RoadSide extends Device {

    /*------------------------ FIELDS REGION ------------------------*/
    private final static double TRUST_LEVEL_INCREASE = 0.1;
    private final static double TRUST_LEVEL_DECREASE = 0.4;

    /*------------------------ METHODS REGION ------------------------*/
    public RoadSide(int id, Point currentLocation, double range) {
        super(id, currentLocation, range);
    }

    @Override
    public void move() {
    }

    @Override
    public void send(Network dynamicNetwork) {
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        event.setRoutingPath(event.getRoutingPath() + "->" + getId());
        return event;
    }

    @Override
    public void receive(Event event) {
        System.out.println("Roadside Received a Message: " + event.toString());
    }

    @Override
    public void turn(CrossRoad crossRoad) {
    }

    @Override
    public void registerTask(Task task) {
    }

    @Override
    public void receiveFakeDevices(Set<Integer> fakeDevices) {
    }
}