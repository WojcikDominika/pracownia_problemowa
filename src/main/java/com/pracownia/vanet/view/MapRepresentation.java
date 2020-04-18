package com.pracownia.vanet.view;

import com.google.common.collect.ImmutableList;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.road.Road;
import com.pracownia.vanet.view.model.DeviceRepresentation;
import com.pracownia.vanet.view.model.Registerable;
import com.pracownia.vanet.view.model.RoadRepresentation;
import javafx.application.Platform;
import javafx.scene.Group;
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
    private List<Registerable> shortLiveViewObjects = new ArrayList<>();
    private Map<Device, DeviceRepresentation> deviceRepresentation = new HashMap<>();
    private Map<Road, RoadRepresentation> roads = new HashMap<>();


    /*------------------------ METHODS REGION ------------------------*/
    public MapRepresentation(ShapeFactory shapeFactory, Group root) {
        this.shapeFactory = shapeFactory;
        this.root = root;
    }


    public DeviceRepresentation getRepresentation(Device device) {
        return deviceRepresentation.computeIfAbsent(device, register(shapeFactory::createDevice));
    }

    public RoadRepresentation getRepresentation(Road road) {
        return roads.computeIfAbsent(road, register(shapeFactory::createRoad));
    }


    public void switchRangeCircles(MapScheme.Range config) {
        for (DeviceRepresentation device : deviceRepresentation.values()) {
            device.setRangeColor(config.color());
        }
    }

    public void drawShortLived(List<? extends Registerable> elementsToDraw) {
        Platform.runLater(() -> {
            elementsToDraw.forEach(element -> element.register(root));
        });
        shortLiveViewObjects.addAll(elementsToDraw);
    }

    public void clearShortLiveObject() {
        List<Registerable> toDeregister = ImmutableList.copyOf(shortLiveViewObjects);
        Platform.runLater(() -> {
            toDeregister.forEach(element -> element.deregister(root));
        });
        shortLiveViewObjects.clear();
    }

    private <I, O extends Registerable> Function<I, O> register(Function<I, O> createDevice) {
        return createDevice.andThen(obj -> {
            Platform.runLater(() -> obj.register(root));
            return obj;
        });
    }
}
    