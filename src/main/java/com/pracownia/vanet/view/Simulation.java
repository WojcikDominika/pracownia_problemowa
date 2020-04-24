package com.pracownia.vanet.view;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.devices.RoadSide;
import com.pracownia.vanet.model.devices.Vehicle;
import com.pracownia.vanet.model.devices.WormholeVehicle;
import com.pracownia.vanet.model.event.Task;
import com.pracownia.vanet.model.network.Connection;
import com.pracownia.vanet.model.network.Network;
import com.pracownia.vanet.model.network.NetworkBuilder;
import com.pracownia.vanet.model.network.connectors.CompositeConnector;
import com.pracownia.vanet.model.network.connectors.DistanceBasedConnector;
import com.pracownia.vanet.model.network.connectors.TunnelConnector;
import com.pracownia.vanet.model.road.CrossRoad;
import com.pracownia.vanet.model.road.Road;
import com.pracownia.vanet.view.model.DeviceRepresentation;
import com.pracownia.vanet.view.model.NetworkConnectionRepresentation;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class Simulation implements Runnable {

    private static final int START_POINTS_NUMBER = 5;
    /*------------------------ FIELDS REGION ------------------------*/
    private Boolean simulationRunning;
    private Thread tr;
    private MapRepresentation mapRepresentation;

    AtomicInteger carCounter = new AtomicInteger(1);
    Random random = new Random();

    private List<Road> roads = new ArrayList<>();
    private List<CrossRoad> crossRoads = new ArrayList<>();
    private ShapeFactory shapeFactory = new ShapeFactory();
    private Collection<Device> devices = Collections.synchronizedCollection(new ArrayList<>());
    private ObservableList<Connection> tunneledDevices =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());


    /*------------------------ METHODS REGION ------------------------*/
    public Simulation( Group scene ) {
        tr = new Thread(this);
        this.simulationRunning = false;
        buildRoads();
        mapRepresentation = new MapRepresentation(shapeFactory, scene);
        roads.forEach(mapRepresentation::getRepresentation);
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

        devices.add(new RoadSide(carCounter.getAndIncrement(), new Point(480.0, 210.0), 50.0));
        devices.add(new RoadSide(carCounter.getAndIncrement(), new Point(260.0, 610.0), 50.0));
        devices.add(new RoadSide(carCounter.getAndIncrement(), new Point(480.0, 610.0), 50.0));
    }

    @Override
    public void run() {
        CompositeConnector connector = CompositeConnector.builder()
                                                         .otherConnector(new DistanceBasedConnector())
                                                         .otherConnector(new TunnelConnector(observingProvider(tunneledDevices)))
                                                         .build();

        while (true) {
            if (simulationRunning) {
                mapRepresentation.clearShortLiveObject();
                // Movement
                move(devices, crossRoads);
                drawDevices(devices);

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

    private Function<Device, Collection<Device>> observingProvider( ObservableList<Connection> tunneledDevices ) {
        //TODO Refactor
        Multimap<Device, Device> tunnels = Multimaps.synchronizedMultimap(MultimapBuilder.hashKeys()
                                                                                         .arrayListValues()
                                                                                         .build());
        synchronized (tunneledDevices) {
            tunneledDevices.forEach(deviceDevicePair -> {
                tunnels.put(deviceDevicePair.getFirstDevice(), deviceDevicePair.getSecondDevice());
                tunnels.put(deviceDevicePair.getSecondDevice(), deviceDevicePair.getFirstDevice());
            });
        }

        tunneledDevices.addListener((ListChangeListener<? super Connection>) change -> {
            while (change.next()) {
                change.getAddedSubList()
                      .forEach(deviceDevicePair -> {
                          tunnels.put(deviceDevicePair.getFirstDevice(), deviceDevicePair.getSecondDevice());
                          tunnels.put(deviceDevicePair.getSecondDevice(), deviceDevicePair.getFirstDevice());
                      });
            }
        });

        return tunnels::get;
    }

    private void drawDevices( Collection<Device> devices ) {
        for (Device device : devices) {
            DeviceRepresentation representation = mapRepresentation.getRepresentation(device);
            representation.move(device.getCurrentLocation());
        }
    }

    private static void move( Collection<Device> devices, Collection<CrossRoad> crossRoads ) {
        synchronized (devices) {
            devices.stream()
                   .forEach(Device::move);
            for (Device device : devices) {
                Optional<CrossRoad> nearestCrossRoad = crossRoads.parallelStream()
                                                                 .map(cr -> Pair.of(cr,
                                                                                    cr.getDistanceToCrossing(device)))
                                                                 .min(Comparator.comparing(Pair::getValue))
                                                                 .filter(pair -> pair.getValue() < CrossRoad.DETECTION_RANGE)
                                                                 .map(Pair::getKey);
                nearestCrossRoad.ifPresent(device::turn);
            }
        }
        crossRoads.parallelStream()
                  .forEach(CrossRoad::resetLastTransportedVehicle);
    }


    private void drawNetworkConnections( Network dynamicNetwork ) {
        Set<Connection> connectedPoints = new HashSet<>();
        synchronized (devices) {
            for (Device device : devices) {
                Set<Device> connectedDevices = dynamicNetwork.getConnectedDevices(device);
                connectedPoints.addAll(connectedDevices.parallelStream()
                                                       .map(d -> Connection.between(d, device))
                                                       .collect(Collectors.toSet()));
            }
        }

        List<NetworkConnectionRepresentation> connectionsToDraw = connectedPoints.parallelStream()
                                                                                 .map(shapeFactory::createNetworkConnection)
                                                                                 .collect(Collectors.toList());
        mapRepresentation.drawShortLived(connectionsToDraw);
    }


    private void simulateCommunication( Network dynamicNetwork ) {
        synchronized (devices) {
            for (Device device : devices) {
                device.send(dynamicNetwork);
            }
        }
    }

    public void switchOffRangeCircles() {
        mapRepresentation.switchRangeCircles(MapScheme.Range.OFF);
    }

    public void switchOnRangeCircles() {
        mapRepresentation.switchRangeCircles(MapScheme.Range.ON);
    }

    public void changeVehiclesRanges( double range ) {
        synchronized (devices) {
            for (Device device : devices) {
                if (device instanceof Vehicle) {
                    device.setRange(range);
                    mapRepresentation.getRepresentation(device)
                                     .setConnectionRange(range);
                }
            }
        }
    }

    public List<Vehicle> addVehicles( int amount ) {
        List<Vehicle> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            result.add(new Vehicle(roads.get(i % START_POINTS_NUMBER),
                                   carCounter.getAndIncrement(),
                                   getCarRange(),
                                   randomizeSpeed()));
        }
        if (devices.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                for (int j = 0; j < devices.size(); j++) {
                    result.get(i)
                          .registerTask(new Task(devices.stream()
                                                        .skip(j)
                                                        .findFirst()
                                                        .get(), "Ala ma kota", 3));
                }
            }
        }
        synchronized (devices) {
            devices.addAll(result);
        }
        return result;
    }

    public List<Vehicle> addWormholeVehicles() {
        Vehicle v1 = new WormholeVehicle(roads.get(0 % START_POINTS_NUMBER),
                                         carCounter.getAndIncrement(),
                                         getCarRange(),
                                         randomizeSpeed());
        Vehicle v2 = new WormholeVehicle(roads.get(1 % START_POINTS_NUMBER),
                                         carCounter.getAndIncrement(),
                                         getCarRange(),
                                         randomizeSpeed());

        synchronized (devices) {
            devices.add(v1);
            devices.add(v2);
        }
        synchronized (tunneledDevices) {
            tunneledDevices.add(Connection.between(v1, v2));
        }
        return Lists.newArrayList(v1, v2);
    }

    private double getCarRange() {
        return 40.0;
    }

    private double randomizeSpeed() {
        return random.nextDouble() / 2.0 + 2;
    }

}
    