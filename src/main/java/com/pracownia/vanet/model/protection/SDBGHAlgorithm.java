package com.pracownia.vanet.model.protection;

import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.devices.RoadSide;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SDBGHAlgorithm {
    Collection<Device> devices;
    private final double ratio = 0.732;

    public SDBGHAlgorithm(Collection<Device> devices) {
        this.devices = devices;
    }


    public List<Device> runAnalysis() {
         return devices.stream()
                 .filter(d -> d.getShouldTransfer().get() != 0 &&
                         !(d instanceof RoadSide) &&
                         d.getTransferred().doubleValue() / d.getShouldTransfer().doubleValue() < ratio)
                 .collect(Collectors.toList());
    }
}
