package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.event.Event;

import java.util.List;
import java.util.Optional;

public class ConnectionRoute {

    /**
     * From start point (with) to endpoint (without)
     */
    List<Device> route;

    /**
     * Endpoint
     */
    Device destination;

    ConnectionRoute(List<Device> route, Device to) {
        this.route = route;
        this.destination = to;
    }

    // To RoadSide
    public void send(Optional<Event> event) {
        route.get(0).incrementOccurrences();
        for (int i = 1; i < route.size(); i++) {
            Device device = route.get(i);
            device.incrementOccurrences();
            // Simulates sending for malicious event manipulation1
            if (event.isPresent()) {
                device.incrementShouldTransfer();
                event = device
                        .transfer(event.get(), route.get(i - 1));
            }
            System.out.println("Id: " + device.getId() + "  ShouldTransfer: " + device.getShouldTransfer());
        }
        if (event.isPresent()) {
            destination.receive(event.get());
        }
    }
}
