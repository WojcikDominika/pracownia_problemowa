package com.pracownia.vanet.model.network.connectors;

import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.network.Connector;
import lombok.Builder;
import lombok.Singular;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class CompositeConnector implements Connector {

    @Singular
    List<Connector> otherConnectors;

    @Override
    public Set<Device> findConnections(Device device, Set<Device> others) {
        return otherConnectors.parallelStream()
                .map(connector -> connector.findConnections(device, others))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
