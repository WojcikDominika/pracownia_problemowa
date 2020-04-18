package com.pracownia.vanet.model.network;

import com.pracownia.vanet.model.devices.Device;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Connection {

    private Device firstDevice;
    private Device secondDevice;

    private Connection(Device firstDevice, Device secondDevice) {
        this.firstDevice = firstDevice;
        this.secondDevice = secondDevice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return firstDevice.equals(that.firstDevice) &&
                secondDevice.equals(that.secondDevice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstDevice.getId(), secondDevice.getId());
    }

    public static Connection between(Device firstDevice, Device secondDevice){
        return new Connection(firstDevice, secondDevice);
    }
}
