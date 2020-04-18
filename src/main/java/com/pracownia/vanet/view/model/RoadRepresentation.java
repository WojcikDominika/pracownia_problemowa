package com.pracownia.vanet.view.model;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class RoadRepresentation implements Registerable {

    private final Line line;

    public RoadRepresentation(Line line) {
        this.line = line;
    }

    @Override
    public void register(Group scene) {
        scene.getChildren().add(line);
    }

    @Override
    public void deregister(Group scene) {
        scene.getChildren().remove(line);
    }
}
