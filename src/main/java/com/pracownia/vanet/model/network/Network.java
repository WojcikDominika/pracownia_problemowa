package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Network {
    Map<Device, NetworkNode> networkNodeByDevice;

    public Network(Map<Device, NetworkNode> networkNodeByDevice) {
        this.networkNodeByDevice = networkNodeByDevice;
    }

    public ConnectionRoute getRoute(Device from, Device to) {
        //TODO Implement route creation
        return new ConnectionRoute(from, to);
    }

    public Set<Device> getConnectedDevices(Device device) {
        if(!networkNodeByDevice.containsKey(device)){
            return Collections.emptySet();
        }
        return networkNodeByDevice.get(device)
                                  .connectedNodes()
                                  .stream()
                                  .map(NetworkNode::device)
                                  .collect(Collectors.toSet());
    }
}
