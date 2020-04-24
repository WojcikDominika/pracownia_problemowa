package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.event.Event;
import java.util.ArrayList;
import java.util.List;

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


    public void send(Event event) {
        route.get(0).incrementOccurrences();
        for (int i = 1; i < route.size(); i++) {
            Device device = route.get(i);
            device.incrementOccurrences();
            // Simulates sending for malicious event manipulation1
            event = device
                    .transfer(event, route.get(i - 1));
        }
        destination.receive(event);
    }
}
