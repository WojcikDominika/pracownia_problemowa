package com.pracownia.vanet.model.protection;

import java.lang.Math;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.devices.RoadSide;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SDBGHAlgorithm {
    Collection<Device> devices;

    public SDBGHAlgorithm(Collection<Device> devices) {
        this.devices = devices;
    }

    private double mean() {
        double result = 0.0;
        int counter = 0;

        for (Device d: this.devices) {
            if (d.getShouldTransfer().doubleValue() > 0) {
                result += d.getTransferred().doubleValue() / d.getShouldTransfer().doubleValue();
                counter++;
            }
        }

        return result / counter;
    }

    private double deviation(double mean) {
        double result = 0.0;
        int counter = 0;

        for (Device d: this.devices) {
            if (d.getShouldTransfer().doubleValue() > 0) {
                double value = d.getTransferred().doubleValue() / d.getShouldTransfer().doubleValue();
                result += Math.pow(value - mean, 2);
                counter++;
            }
        }

        return Math.sqrt(result / counter);
    }

    public List<Device> runAnalysis() {
        double mean = this.mean();
        double deviation = this.deviation(mean);

        return devices.stream()
                 .filter(d -> d.getShouldTransfer().get() != 0 &&
                         !(d instanceof RoadSide) && Math.abs(
                             ((d.getTransferred().doubleValue() / d.getShouldTransfer().doubleValue()) - mean) / deviation
                     ) >= 1)
                 .collect(Collectors.toList());
    }
}
