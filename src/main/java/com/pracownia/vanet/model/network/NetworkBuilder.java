package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.devices.RoadSide;
import com.pracownia.vanet.model.devices.Vehicle;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;

public class NetworkBuilder {

    private Set<Device> devices;
    private List<Pair<Device, Device>> additionalConnections;

    public NetworkBuilder withVehicles(List<Vehicle> vehicles) {
        devices.addAll(vehicles);
        return this;
    }

    public NetworkBuilder withRoadSides(List<RoadSide> roadSides) {
        devices.addAll(roadSides);
        return this;
    }

    public NetworkBuilder withAdditionalConnections(List<Pair<Device, Device>> additionalConnections) {
        this.additionalConnections.addAll(additionalConnections);
        return this;
    }

    public Network build() {
        // TODO implement
        return new Network();
    }

}
