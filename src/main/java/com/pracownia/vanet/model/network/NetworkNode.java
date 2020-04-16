package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NetworkNode {

    Device device;
    List<NetworkNode> connectedNodes;

    public NetworkNode(Device device) {
        this.device = device;
        connectedNodes = new ArrayList<>();
    }

    public Device device() {
        return device;
    }

    public void addConnectedNodes(List<NetworkNode> connectedNodes) {
        this.connectedNodes.addAll(connectedNodes);
    }

    public Collection<NetworkNode> connectedNodes() {
        return connectedNodes;
    }
}
