package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.ConnectionRoute;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.CrossRoad;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
public class SIN extends Device {

    /*------------------------ FIELDS REGION ------------------------*/
    private Set<String> trustedDevices = new HashSet<>();
    protected Task task;

    /*------------------------ METHODS REGION ------------------------*/
    public SIN(int id, Point currentLocation, double range) {
        super(id, currentLocation, range);
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
            if (device instanceof Vehicle) {
                task.prepareEvent(this).ifPresent(event -> {
                    event.setIdentityCheck(true);
                    event.setTarget(device);
                    Optional<ConnectionRoute> route = dynamicNetwork.getRoute(this, event.getTarget());
                    event.setRoutingPath(String.valueOf(id));
                    route.ifPresent(r -> r.send(event));
                });
            }
        });
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        return new Event();
    }

    @Override
    public void receive (Event event) {
//        this.trustedDevices.forEach(id -> System.out.println(id));
        if (!this.trustedDevices.contains(event.getMessage())) {
            System.out.println("Blackhole detected!");
            this.fakeDevices.add(event.getSource().getId());
        }

        if (!this.fakeDevices.isEmpty()) {
//            String fakeIds = "";
//            for (Integer id : this.fakeDevices) {
//                fakeIds += id + ",";
//            }
//            event.getSource().receive(new Event(event.getId(), this, event.getSource(), new Date(), fakeIds, "" + id));
            event.getSource().receiveFakeDevices(this.fakeDevices);
        }
    }

    @Override
    public void turn(CrossRoad crossRoad) {
        //Does not move
    }

    @Override
    public void receiveFakeDevices(Set<Integer> fakeDevices) {
        //Nothing
    }

    @Override
    public void registerTask(Task task)  {
        this.task = task;
    }
}
