package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.ConnectionRoute;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.CrossRoad;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class SIN extends Device {

    /*------------------------ FIELDS REGION ------------------------*/
    private List<UUID> trustedDevices = new ArrayList<>();
    protected Task task;

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
        if (task == null) {
            return;
        }

        dynamicNetwork.getConnectedDevices(this).forEach(device -> {
            task.prepareEvent(this).ifPresent(event -> {
                event.identityCheck = true;
                event.setTarget(device);
                Optional<ConnectionRoute> route = dynamicNetwork.getRoute(this, event.getTarget());
                event.setRoutingPath(String.valueOf(id));
                route.ifPresent(r -> r.send(event));
            });
        });
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        return new Event();
    }

    @Override
    public void receive (Event event) {
        System.out.println(event.toString());
    }

    @Override
    public void turn(CrossRoad crossRoad) {
        //Does not move
    }

    @Override
    public void registerTask(Task task)  {
        this.task = task;
    }
}
