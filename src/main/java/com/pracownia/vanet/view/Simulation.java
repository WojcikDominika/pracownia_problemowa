package com.pracownia.vanet.view;

import com.google.common.collect.Lists;
import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.network.NetworkBuilder;
import com.pracownia.vanet.model.network.connectors.CompositeConnector;
import com.pracownia.vanet.model.network.connectors.DistanceBasedConnector;
import com.pracownia.vanet.model.network.connectors.TunnelConnector;
import com.pracownia.vanet.model.road.CrossRoad;
import com.pracownia.vanet.model.devices.Vehicle;
import com.pracownia.vanet.model.devices.RoadSide;
import com.pracownia.vanet.model.road.Road;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Setter
public class Simulation implements Runnable {

    /*------------------------ FIELDS REGION ------------------------*/
    private Color here = Color.RED;
    private Boolean simulationRunning;
    private Thread tr;
    private MapRepresentation mapRepresentation;

    AtomicInteger carCounter = new AtomicInteger(1);
    Random random = new Random();

    private List<Road> roads = new ArrayList<>();
    private List<CrossRoad> crossRoads= new ArrayList<>();
    private List<Device> devices= new ArrayList<>();
    private ShapeFactory shapeFactory = new ShapeFactory();


    /*------------------------ METHODS REGION ------------------------*/
    public Simulation(Group scene) {
        tr = new Thread(this);
        this.simulationRunning = false;
        buildRoads();
        mapRepresentation = new MapRepresentation(shapeFactory, scene);
        mapRepresentation.draw(roads.stream()
                                    .map(road -> Pair.of(road.getStartPoint(), road.getEndPoint()))
                                    .map(pair -> shapeFactory.createLine(pair, Color.LIGHTGRAY))
                                    .collect(Collectors.toSet()));
    }

    private void buildRoads() {
        roads.add(new Road(200.0, 100.0, 200.0, 700.0));
        roads.add(new Road(400.0, 100.0, 400.0, 700.0));
        roads.add(new Road(600.0, 100.0, 600.0, 700.0));
        roads.add(new Road(800.0, 100.0, 800.0, 700.0));
        roads.add(new Road(100.0, 200.0, 900.0, 200.0));
        roads.add(new Road(100.0, 400.0, 900.0, 400.0));
        roads.add(new Road(100.0, 600.0, 900.0, 600.0));

        crossRoads.add(new CrossRoad(new Point(200.0, 200.0), roads.get(0), roads.get(4)));
        crossRoads.add(new CrossRoad(new Point(200.0, 400.0), roads.get(0), roads.get(5)));
        crossRoads.add(new CrossRoad(new Point(200.0, 600.0), roads.get(0), roads.get(6)));
        crossRoads.add(new CrossRoad(new Point(400.0, 200.0), roads.get(1), roads.get(4)));
        crossRoads.add(new CrossRoad(new Point(400.0, 400.0), roads.get(1), roads.get(5)));
        crossRoads.add(new CrossRoad(new Point(400.0, 600.0), roads.get(1), roads.get(6)));
        crossRoads.add(new CrossRoad(new Point(600.0, 200.0), roads.get(2), roads.get(4)));
        crossRoads.add(new CrossRoad(new Point(600.0, 400.0), roads.get(2), roads.get(5)));
        crossRoads.add(new CrossRoad(new Point(600.0, 600.0), roads.get(2), roads.get(6)));
        crossRoads.add(new CrossRoad(new Point(800.0, 200.0), roads.get(3), roads.get(4)));
        crossRoads.add(new CrossRoad(new Point(800.0, 400.0), roads.get(3), roads.get(5)));
        crossRoads.add(new CrossRoad(new Point(800.0, 600.0), roads.get(3), roads.get(6)));

        devices.add(new RoadSide(0, new Point(480.0, 210.0), 30.0));
        devices.add(new RoadSide(1, new Point(260.0, 610.0), 30.0));
        devices.add(new RoadSide(2, new Point(480.0, 610.0), 30.0));
    }

    @Override
    public void run() {
        CompositeConnector connector = CompositeConnector.builder()
                                                         .otherConnector(new DistanceBasedConnector())
                                                         .otherConnector(new TunnelConnector(Lists.newArrayList()))
                                                         .build();

        while (true) {
            if (simulationRunning) {
                // Movement
                move(devices);
                drawDevices(devices);
                turnOnCrossRoads(devices, crossRoads);

                // Build Network Connections
                Network dynamicNetwork = new NetworkBuilder(connector)
                        .withDevices(this.devices)
                        .build();
                simulateCommunication(dynamicNetwork);
                drawNetworkConnections(dynamicNetwork);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawDevices(List<Device> devices) {
        for (Device device : devices) {
            DeviceRepresentation representation = mapRepresentation.getRepresentation(device);
            representation.move(device.getCurrentLocation());
        }
    }

    private static void move(Collection<Device> devices) {
        devices.stream().forEach(Device::move);
    }


    private void drawNetworkConnections(Network dynamicNetwork) {
        Set<Pair<Point, Point>> connectedPoints = new HashSet<>();

        for(Device device: devices){
            Set<Device> connectedDevices = dynamicNetwork.getConnectedDevices(device);
            Set<Pair<Point, Point>> lines = connectedDevices.parallelStream()
                                                            .map(d -> Pair.of(d.getCurrentLocation(), device.getCurrentLocation()))
                                                            .collect(Collectors.toSet());
            connectedPoints.addAll(lines);
        }

        List<Line> linesToDraw = connectedPoints.parallelStream()
                                                .map(pair -> shapeFactory.createLine(pair, Color.BLUE))
                                                .collect(Collectors.toList());
        mapRepresentation.drawConnections(linesToDraw);
    }


    private void simulateCommunication(Network dynamicNetwork) {
        for (Device device : devices) {
//            device.send(dynamicNetwork);
        }
    }

    private static void turnOnCrossRoads(List<Device> devices, List<CrossRoad> crossRoads) {
        for (Device device : devices) {
            Optional<CrossRoad> nearestCrossRoad = crossRoads.parallelStream()
                                                             .map(cr -> Pair.of(cr, cr.getDistanceToCrossing(device)))
                                                             .min(Comparator.comparing(Pair::getValue))
                                                             .filter(pair -> pair.getValue() < CrossRoad.DETECTION_RANGE)
                                                             .map(Pair::getKey);
            nearestCrossRoad.ifPresent(device::turn);
        }
        crossRoads.parallelStream().forEach(CrossRoad::resetLastTransportedVehicle);
    }

    public void switchOffRangeCircles() {
        mapRepresentation.switchRangeCircles(MapScheme.Range.OFF);
    }

    public void switchOnRangeCircles() {
        mapRepresentation.switchRangeCircles(MapScheme.Range.ON);
    }

    public void changeVehiclesRanges(double range) {
        for (Device device : devices) {
            if(device instanceof Vehicle){
                device.setRange(range);
                mapRepresentation.getRepresentation(device).setConnectionRange(range);
            }
        }
    }

    public void addVehicles(int amount) {
        for (int i = 0; i < amount; i++) {
            devices.add(new Vehicle(roads.get(i % 5),
                                     carCounter.getAndIncrement(),
                                     getCarRange(),
                                     randomizeSpeed()));
        }
    }

    private double getCarRange() {
        return 40.0;
    }

    private double randomizeSpeed() {
        return random.nextDouble() / 2.0 + 2;
    }

}
    