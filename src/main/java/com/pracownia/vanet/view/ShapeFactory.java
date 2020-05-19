package com.pracownia.vanet.view;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.devices.*;
import com.pracownia.vanet.model.network.Connection;
import com.pracownia.vanet.model.road.Road;
import com.pracownia.vanet.view.model.DeviceRepresentation;
import com.pracownia.vanet.view.model.NetworkConnectionRepresentation;
import com.pracownia.vanet.view.model.RoadRepresentation;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.Set;

public class ShapeFactory {
    private Set<DeviceRepresentation> deviceRepresentations = new HashSet<>();

    public DeviceRepresentation createDevice(Device device) {
        DeviceRepresentation deviceRepresentation = new DeviceRepresentation(label(device.getId()),
                                                                             devicePointCircle(device),
                                                                             deviceRange(device));
        if (device instanceof BlackholeVehicle) {
            deviceRepresentation.setColor(Color.GREEN);
        } else if (device instanceof Vehicle) {
            deviceRepresentation.setColor(Color.BLACK);
        } else if (device instanceof RoadSide) {
            deviceRepresentation.setColor(Color.TEAL);
        } else if (device instanceof CarAccident) {
            deviceRepresentation.setColor(Color.RED);
        }

        this.deviceRepresentations.add(deviceRepresentation);

        return deviceRepresentation;
    }

    public NetworkConnectionRepresentation createNetworkConnection(Connection connection) {
        NetworkConnectionRepresentation connectionRepresentation = new NetworkConnectionRepresentation(connectionLine(connection));
        if (connection.getFirstDevice() instanceof WormholeVehicle && connection.getSecondDevice() instanceof WormholeVehicle) {
            connectionRepresentation.setColor(Color.DARKMAGENTA);
        }
        return connectionRepresentation;
    }

    private static Text label(int id) {
        Text text = new Text(String.valueOf(id));
        text.setLayoutX(0);
        text.setLayoutY(0);
        return text;
    }

    private static Circle devicePointCircle(Device device) {
        Circle circle = new Circle();
        circle.setCenterX(device.getCurrentLocation().getX());
        circle.setCenterY(device.getCurrentLocation().getY());
        circle.setFill(Color.BLACK);
        circle.setRadius(6.0);
        return circle;
    }

    private static Line connectionLine(Connection connection) {
        Line line = new Line();
        Point firstDevice = connection.getFirstDevice().getCurrentLocation();
        Point secondDevice = connection.getSecondDevice().getCurrentLocation();

        line.setStartX(firstDevice.getX());
        line.setStartY(firstDevice.getY());
        line.setEndX(secondDevice.getX());
        line.setEndY(secondDevice.getY());

        line.setFill(Color.DARKTURQUOISE);
        line.setStroke(Color.DARKTURQUOISE);
        return line;
    }

    private static Circle deviceRange(Device device) {
        Circle circle = new Circle();
        circle.setRadius(device.getRange());
        circle.setCenterX(device.getCurrentLocation().getX());
        circle.setCenterY(device.getCurrentLocation().getY());
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.TRANSPARENT);
        return circle;
    }

    public RoadRepresentation createRoad(Road road) {
        Line line = new Line();
        line.setStartX(road.getStartPoint().getX());
        line.setStartY(road.getStartPoint().getY());
        line.setEndX(road.getEndPoint().getX());
        line.setEndY(road.getEndPoint().getY());
        line.setFill(Color.LIGHTGRAY);
        line.setStroke(Color.LIGHTGRAY);
        return new RoadRepresentation(line);
    }
}

