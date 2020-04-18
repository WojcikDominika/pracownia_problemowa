package com.pracownia.vanet.view.model;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class NetworkConnectionRepresentation implements Registerable {

    Line deviceConnection;

    public NetworkConnectionRepresentation(Line deviceConnection) {
        this.deviceConnection = deviceConnection;
    }

    @Override
    public void register(Group scene) {
        scene.getChildren().add(deviceConnection);
    }

    @Override
    public void deregister(Group scene) {
        scene.getChildren().remove(deviceConnection);
    }

    public void setColor(Color color) {
        Platform.runLater(() -> {
            deviceConnection.setFill(color);
            deviceConnection.setStroke(color);
        });
    }
}
