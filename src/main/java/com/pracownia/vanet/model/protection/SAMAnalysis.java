package com.pracownia.vanet.model.protection;

import com.pracownia.vanet.model.devices.Device;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SAMAnalysis {
    Collection<Device> devices;

    private SAMAnalysis() {
    }

    public SAMAnalysis( Collection<Device> devices ) {
        this.devices = devices;
    }

    private Double countMean() {
        Mean mean = new Mean();
        return mean.evaluate(getOccurrencesAsArray());
    }

    private Double countStdDeviation() {
        StandardDeviation deviation = new StandardDeviation();
        return deviation.evaluate(getOccurrencesAsArray());
    }

    public List<Device> runAnalysis() {
        Double mean = countMean();
        Double deviation = countStdDeviation() * 1.5;
        Double result = mean + deviation;
        if (mean < 5) {
            return new ArrayList<>();
        }
        return this.devices.stream()
                           .filter(device -> device.getOccurrences()
                                                   .get() > result)
                           .collect(Collectors.toList());
    }

    private double[] getOccurrencesAsArray() {
        return this.devices.stream()
                           .mapToDouble(dev -> dev.getOccurrences()
                                                  .get())
                           .toArray();
    }
}