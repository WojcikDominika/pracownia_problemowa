package com.pracownia.vanet.model.network.connectors;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.network.Connection;
import com.pracownia.vanet.model.network.Connector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class TunnelConnector implements Connector {

    private final Function<Device, Collection<Device>> connectionSupplier;

    public TunnelConnector(Function<Device, Collection<Device>> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Set<Device> findConnections(Device device, Set<Device> others) {
        return new HashSet(connectionSupplier.apply(device));
    }

}
