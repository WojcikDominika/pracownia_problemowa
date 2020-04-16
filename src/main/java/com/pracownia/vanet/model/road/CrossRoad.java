package com.pracownia.vanet.model.road;

import com.pracownia.vanet.model.Point;
import com.pracownia.vanet.model.devices.Device;
import com.pracownia.vanet.model.devices.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public class CrossRoad {

    /*------------------------ FIELDS REGION ------------------------*/
    public static final double DETECTION_RANGE = 1.0;

    private Point location;
    private Road roadA;
    private Road roadB;
    private Vehicle lastTransportedVehicle = new Vehicle();

    /*------------------------ METHODS REGION ------------------------*/
    public CrossRoad(Point location, Road roadA, Road roadB) {
        this.location = location;
        this.roadA = roadA;
        this.roadB = roadB;
    }

    public void transportVehicle(Vehicle vehicle) {
        if (vehicle == lastTransportedVehicle) {
            return;
        }

        lastTransportedVehicle = vehicle;
        Random random = new Random();
        int pom = random.nextInt();

        if (Math.abs(pom % 3) == 0 || Math.abs(pom % 3) == 1) {
            if (vehicle.getRoad() == roadA) {
                vehicle.setRoad(roadB);
            } else {
                vehicle.setRoad(roadA);
            }

            vehicle.setCurrentLocation(new Point(location.getX(), location.getY()));

            if (Math.abs(pom % 3) == 0) {
                vehicle.setDirection(!vehicle.isDirection());
            }

        }

        if (vehicle.getPreviousCrossing() != null && vehicle.getPreviousCrossing() != this.location) {
            double s = Math.sqrt(Math.pow((location.getX() - vehicle.getPreviousCrossing()
                    .getX()), 2) + Math.pow(location.getY() - vehicle.getPreviousCrossing()
                    .getY(), 2));
            double t = Math.abs(new Date().getTime() - vehicle.getDate().getTime());
        }
        vehicle.setPreviousCrossing(location);
    }

    public double getDistanceToCrossing(Device vehicle) {
        return Math.sqrt(Math.pow(location.getX() - vehicle.getCurrentLocation().getX(), 2) +
                Math.pow(location.getY() - vehicle.getCurrentLocation().getY(), 2));
    }

    public void resetLastTransportedVehicle() {
        if (getDistanceToCrossing(lastTransportedVehicle) > CrossRoad.DETECTION_RANGE) {
            lastTransportedVehicle = new Vehicle();
        }
    }
}
    