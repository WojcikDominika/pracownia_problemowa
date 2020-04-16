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

    ConnectionRoute(Device from, Device to) {
        this.route = new ArrayList<>();
        this.destination = to;
    }

    public void send(Event event) {
        for (int i = 1; i < route.size(); i++) {
            // Simulates sending for malicious event manipulation
            event = route.get(i).transfer(event, route.get(i - 1));
        }
        destination.receive(event);
    }
}
