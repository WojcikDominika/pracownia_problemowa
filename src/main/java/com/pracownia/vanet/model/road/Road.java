package com.pracownia.vanet.model.road;

import com.pracownia.vanet.model.Point;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
public class Road {

    /*------------------------ FIELDS REGION ------------------------*/
    private Point startPoint;
    private Point endPoint;

    /*------------------------ METHODS REGION ------------------------*/
    public Road() {
        startPoint = new Point();
        endPoint = new Point();
    }

    public Road(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Road(double xStartPoint, double yStartPoint, double xEndPoint, double yEndPoint) {
        this.startPoint = new Point(xStartPoint, yStartPoint);
        this.endPoint = new Point(xEndPoint, yEndPoint);

    }

    public double getDistance() {
        return Math.sqrt(Math.pow(endPoint.getX() - startPoint.getX(), 2)
                + Math.pow(endPoint.getY() - startPoint.getY(), 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Road road = (Road) o;

        return new EqualsBuilder()
                .append(startPoint, road.startPoint)
                .append(endPoint, road.endPoint)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(startPoint)
                .append(endPoint)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("startPoint", startPoint)
                .append("endPoint", endPoint)
                .toString();
    }
}
    