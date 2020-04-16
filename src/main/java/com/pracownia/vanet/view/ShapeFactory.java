package com.pracownia.vanet.view;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.devices.RoadSide;
import com.pracownia.vanet.model.devices.Vehicle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.apache.commons.lang3.tuple.Pair;

public class ShapeFactory {


    public Line createLine(Pair<Point, Point> pointPointPair, Color color) {
        Point start = pointPointPair.getLeft();
        Point end = pointPointPair.getRight();
        Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
        line.setFill(color);
        line.setStroke(color);
        return line;
    }

    public DeviceRepresentation createDevice(Device device) {
        DeviceRepresentation deviceRepresentation = new DeviceRepresentation(deviceLabel(device.getId()),
                                                                             devicePointCircle(device),
                                                                             deviceRange(device));
        if(device instanceof Vehicle){
            deviceRepresentation.setColor(Color.BLACK);
        } else if(device instanceof RoadSide){
            deviceRepresentation.setColor(Color.DARKBLUE);
        }
        return deviceRepresentation;
    }

    private static Text deviceLabel(int id){
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

    private static Circle deviceRange(Device device) {
        Circle circle = new Circle();
        circle.setRadius(device.getRange());
        circle.setCenterX(device.getCurrentLocation().getX());
        circle.setCenterY(device.getCurrentLocation().getY());
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.TRANSPARENT);
        return circle;
    }
}
