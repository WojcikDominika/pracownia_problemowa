package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;

import java.util.List;

public class NetworkNode {

    Device device;
    List<NetworkNode> connectedNodes;
}
