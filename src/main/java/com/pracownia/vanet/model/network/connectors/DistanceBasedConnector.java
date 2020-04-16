package com.pracownia.vanet.model.network.connectors;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.network.Connector;

import java.util.Set;
import java.util.stream.Collectors;

public class DistanceBasedConnector implements Connector {
    @Override
    public Set<Device> findConnections(Device device, Set<Device> others) {
        return others.stream()
                .filter(other -> isInRange(device, other))
                .collect(Collectors.toSet());
    }

    private static boolean isInRange(Device device, Device other) {
        double distance = calculateDistance(device.getCurrentLocation(), other.getCurrentLocation());
        return distance <= device.getRange() + other.getRange();
    }

    private static double calculateDistance(Point loc1, Point loc2) {
        double x1 = loc1.getX();
        double y1 = loc1.getY();
        double x2 = loc2.getX();
        double y2 = loc2.getY();
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
