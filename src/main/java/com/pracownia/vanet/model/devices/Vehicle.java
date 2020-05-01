package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.ConnectionRoute;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.CrossRoad;
import com.pracownia.vanet.model.road.Road;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Vehicle extends Device {

    /*------------------------ FIELDS REGION ------------------------*/
    protected int id;
    protected Road road;
    protected double speed;
    protected boolean direction = true; // True if from starting point to end point
    protected Date date = new Date();
    protected List<Task> tasks;
    @Setter(AccessLevel.NONE)
    private Point previousCrossing;


    /*------------------------ METHODS REGION ------------------------*/
    public Vehicle() {
        this.tasks = new ArrayList<>();
        road = new Road();
        currentLocation = new Point();
    }

    public Vehicle(Road road, int id, double range, double speed) {
        this.tasks = new ArrayList<>();
        this.road = road;
        this.id = id;
        this.range = range;
        this.speed = speed + 0.001;
        this.currentLocation = new Point(road.getStartPoint().getX(), road.getStartPoint().getY());
    }

    public void setPreviousCrossing(Point previousCrossing) {
        this.previousCrossing = previousCrossing;
        this.setDate(new Date());
    }

    @Override
    public void move() {
        double distanceToEndPoint = Math.sqrt(Math.pow(road.getEndPoint()
                                                           .getX() - currentLocation.getX(), 2) +
                                                      Math.pow(road.getEndPoint().getY() - currentLocation.getY(), 2));

        double cos = (road.getEndPoint().getX() - currentLocation.getX()) / distanceToEndPoint;
        double sin = (road.getEndPoint().getY() - currentLocation.getY()) / distanceToEndPoint;

        double distanceToStart;

        if (direction) {
            distanceToStart = Math.sqrt(Math.pow(currentLocation.getX() - road.getStartPoint()
                                                                              .getX(), 2) +
                                                Math.pow(currentLocation.getY() - road.getStartPoint().getY(), 2));
            currentLocation.setX(currentLocation.getX() + cos * speed);
            currentLocation.setY(currentLocation.getY() + sin * speed);
        } else {
            distanceToStart = Math.sqrt(Math.pow(currentLocation.getX() - road.getEndPoint()
                                                                              .getX(), 2) +
                                                Math.pow(currentLocation.getY() - road.getEndPoint().getY(), 2));

            currentLocation.setX(currentLocation.getX() - cos * speed);
            currentLocation.setY(currentLocation.getY() - sin * speed);
        }

        if (distanceToStart >= road.getDistance()) {
            direction = !direction;
        }
    }

    @Override
    public void send(Network dynamicNetwork) {
        if (tasks.isEmpty()) {
            return;
        }
        tasks.stream()
             .map(task -> task.prepareEventFor(this))
             .filter(Optional::isPresent)
             .map(Optional::get)
             .forEach(event -> dynamicNetwork.getRoute(this, event.getTarget())
                                             .ifPresent(r -> r.send(event)));
    }

    /*task.prepareEvent(this).ifPresent(event -> {
            Optional<ConnectionRoute> route = dynamicNetwork.getRoute(this, event.getTarget());
            event.setRoutingPath(String.valueOf(id));
            route.ifPresent(r -> r.send(event));
        });*/


    @Override
    public Event transfer(Event event, Device receivedFrom) {
        if (!event.getRoutingPath().contains("->")) {
            this.tasks.forEach(task -> task.done = true);
        }
        event.setRoutingPath(event.getRoutingPath() + "->" + id);
        return event;
    }

    @Override
    public void receive(Event event) {
        if (event.ifIdentityCheck()) {
            System.out.println("Vehicle Received a Message: " + event.toString());
            event.getSource().receive(new Event(event.getId(), this, event.getSource(), new Date(), this.privateId.toString(), "" + id));
        }
//        if (event.getSource() instanceof SIN && !event.ifIdentityCheck()) {
//            for (String id : event.getMessage().split(",")) {
//                this.tr
//            }
//            System.out.println(event.toString());
//        }
    }

    @Override
    public void turn(CrossRoad crossRoad) {
        crossRoad.transportVehicle(this);
    }

    @Override
    public void registerTask(Task task) {
        this.tasks.add(task);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", road=" + road +
                ", speed=" + speed +
                ", direction=" + direction +
                ", date=" + date +
                ", previousCrossing=" + previousCrossing +
                ", task=" + tasks.toString() +
                ", id=" + id +
                ", currentLocation=" + currentLocation +
                ", range=" + range +
                ", privateId=" + privateId +
                ", fakeDevices=" + fakeDevices +
                '}';
    }

    @Override
    public void receiveFakeDevices(Set<Integer> fakeDevices) {
        this.fakeDevices.addAll(fakeDevices);
    }
}
    