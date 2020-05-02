package com.pracownia.vanet.model.network;

import com.google.common.base.Functions;
import com.google.common.collect.Sets;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.devices.RoadSide;
import com.pracownia.vanet.model.devices.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkBuilder {

    private Set<Device> devices = new HashSet<>();
    private Connector connector;

    public NetworkBuilder(Connector connector) {
        this.connector = connector;
    }

    public NetworkBuilder withDevices(Collection<Device> devices) {
        this.devices.addAll(devices);
        return this;
    }

    public Network build() {
        Map<Device, NetworkNode> networkNodeByDevice = devices.stream()
                .map(NetworkNode::new)
                .collect(Collectors.toMap(NetworkNode::device, Functions.identity()));

        for (Device device : devices) {
            Set<Device> otherDevices = Sets.difference(devices, Sets.newHashSet(device));
            Set<Device> connected = connector.findConnections(device, otherDevices);
            List<NetworkNode> connectedNodes = connected.stream()
                    .map(networkNodeByDevice::get)
                    .collect(Collectors.toList());
            networkNodeByDevice.get(device).addConnectedNodes(connectedNodes);
        }

        return new Network(networkNodeByDevice);
    }

}
