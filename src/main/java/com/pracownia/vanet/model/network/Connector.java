package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;

import java.util.Set;

public interface Connector {

    Set<Device> findConnections(Device device, Set<Device> others);
}
