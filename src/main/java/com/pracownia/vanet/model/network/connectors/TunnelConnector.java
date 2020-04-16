package com.pracownia.vanet.model.network.connectors;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.network.Connector;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class TunnelConnector implements Connector {

    Multimap<Device, Device> tunnels = MultimapBuilder.hashKeys().arrayListValues().build();

    public TunnelConnector(List<Pair<Device, Device>> connectedDevices) {
        connectedDevices.stream().forEach(deviceDevicePair -> {
            this.tunnels.put(deviceDevicePair.getLeft(), deviceDevicePair.getRight());
            this.tunnels.put(deviceDevicePair.getRight(), deviceDevicePair.getLeft());
        });
    }

    @Override
    public Set<Device> findConnections(Device device, Set<Device> others) {
        return new HashSet(tunnels.get(device));
    }
}
