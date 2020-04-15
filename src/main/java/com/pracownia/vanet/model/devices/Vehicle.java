package com.pracownia.vanet.model.devices;

import com.pracownia.vanet.model.event.Event;
import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.network.ConnectionRoute;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.road.Road;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Vehicle extends Device {

    /*------------------------ FIELDS REGION ------------------------*/
    private int id;
    private double trustLevel;
    private double currentX;
    private double currentY;
    private Road road;
    private int iterator;
    private double speed;
    private boolean direction = true; // True if from starting point to end point
    private List<RoadSide> connectedPoints = new ArrayList<>();

    private Date date;
    @Setter(AccessLevel.NONE)
    private Point previousCrossing;
    private boolean safe = true;

    /*------------------------ METHODS REGION ------------------------*/
    public Vehicle() {
        road = new Road();
        trustLevel = 0.5;
        currentLocation = new Point();
    }

    public Vehicle(Road road, int id, double range, double speed) {
        this.road = road;
        this.id = id;
        this.range = range;
        this.speed = speed + 0.001;
        trustLevel = 0.5;
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
        // TODO: simulate message sending
        Device target = null;
        ConnectionRoute route = dynamicNetwork.getRoute(this, target);
        route.send(new Event());
    }

    @Override
    public Event transfer(Event event, Device receivedFrom) {
        return event;
    }

    @Override
    public void receive(Event event) {
        System.out.println("Message Received: " + event.toString());
    }

    @Override
    public String toString() {
        return "ID:\t" + id + '\t' + "safe: " + safe;
    }
}
    