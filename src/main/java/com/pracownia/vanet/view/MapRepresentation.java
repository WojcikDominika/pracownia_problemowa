package com.pracownia.vanet.view;

import com.google.common.collect.ImmutableList;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.road.Road;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;

@Getter
@Setter
public class MapRepresentation {

    /*------------------------ FIELDS REGION ------------------------*/
    private double width = 1000.0;
    private double height = 900.0;

    private Group root;
    private final ShapeFactory shapeFactory;
    private List<Line> networkRepresentation = new ArrayList<>();
    private Map<Device, DeviceRepresentation> deviceRepresentation = new HashMap<>();


    /*------------------------ METHODS REGION ------------------------*/
    public MapRepresentation(ShapeFactory shapeFactory, Group root) {
        this.shapeFactory = shapeFactory;
        this.root = root;
    }


    public DeviceRepresentation getRepresentation(Device device) {
        return deviceRepresentation.computeIfAbsent(device, register(shapeFactory::createDevice));
    }

    private Function<Device, DeviceRepresentation> register(Function<Device, DeviceRepresentation> createDevice) {
        return createDevice.andThen(obj -> {
            Platform.runLater(() -> obj.register(root));
            return obj;
        });
    }

    public void drawConnections(List<Line> linesToDraw) {
        List<Node> toRemove = ImmutableList.copyOf(networkRepresentation);
        Platform.runLater(() -> {
            root.getChildren().removeAll(toRemove);
            linesToDraw.forEach(line -> root.getChildren().add(line));
        });
        networkRepresentation = new ArrayList<>(linesToDraw);
    }

    public void switchRangeCircles(MapScheme.Range config) {
        for (DeviceRepresentation device : deviceRepresentation.values()) {
            device.setRangeColor(config.color());
        }
    }

    public void draw(Set<Line> roads) {
        Platform.runLater(() -> {
            roads.forEach(line -> root.getChildren().add(line));
        });
    }
}
    