package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.CrossRoad;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SIN extends Device {

    /*------------------------ FIELDS REGION ------------------------*/
    private List<UUID> trustedDevices = new ArrayList<>();

    /*------------------------ METHODS REGION ------------------------*/
    public SIN(int id, Point currentLocation, double range) {
        super(id, currentLocation, range);
    }

    public void addTrustedDevices(List<UUID> trustedDevices) {
        this.trustedDevices.addAll(trustedDevices);
    }

    @Override
    public void move() {
        //Does not move
    }

    @Override
    public void send(Network dynamicNetwork) {
        //Nothing
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        return new Event();
    }

    @Override
    public void receive(Event event) {
        //TODO
    }

    @Override
    public void turn(CrossRoad crossRoad) {
        //Does not move
    }

    @Override
    public void registerTask(Task task) {
        //No tasks?
    }
}
