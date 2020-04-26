package com.pracownia.vanet.view.model;

import com.pracownia.vanet.model.Point;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class DeviceRepresentation extends Node implements Registerable {
    private static final double TEXT_SHIFT = 6.0;
    Circle deviceLocalisation;
    Circle deviceConnectionRange;
    Text text;

    public DeviceRepresentation(Text text, Circle devicePointCircle, Circle deviceRange) {
        deviceLocalisation = devicePointCircle;
        deviceConnectionRange = deviceRange;
        this.text = text;
        updateLabelLocation();
    }


    public void setConnectionRange(double range) {
        deviceConnectionRange.setRadius(range);
    }

    public void move(Point currentLocation) {
        double vehicleX = currentLocation.getX();
        double vehicleY = currentLocation.getY();

        Platform.runLater(() -> {
            deviceLocalisation.setCenterX(vehicleX);
            deviceLocalisation.setCenterY(vehicleY);
            deviceConnectionRange.setCenterX(vehicleX);
            deviceConnectionRange.setCenterY(vehicleY);
            updateLabelLocation();
        });
    }

    private void updateLabelLocation() {
        this.text.setLayoutX(deviceLocalisation.getCenterX() + TEXT_SHIFT);
        this.text.setLayoutY(deviceLocalisation.getCenterY() + TEXT_SHIFT);
    }

    @Override
    public void register(Group scene) {
        scene.getChildren().add(deviceLocalisation);
        scene.getChildren().add(deviceConnectionRange);
        scene.getChildren().add(text);
    }

    @Override
    public void deregister(Group scene) {
        scene.getChildren().remove(deviceLocalisation);
        scene.getChildren().remove(deviceConnectionRange);
        scene.getChildren().remove(text);
    }

    public void setRangeColor(Paint color) {
        deviceConnectionRange.setStroke(color);
    }

    public void setColor(Color color) {
        deviceLocalisation.setFill(color);
    }

    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }
}
