package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Network {
    List<NetworkNode> allNetworkNodes;
    Map<Device, NetworkNode> deviceToNetworkNode;

    public ConnectionRoute getRoute(Device from, Device to){
        //TODO Implement route creation
        return new ConnectionRoute();
    }
}
