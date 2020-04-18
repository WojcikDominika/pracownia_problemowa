package com.pracownia.vanet.view.model;

import javafx.scene.Group;

public interface Registerable {
    void register(Group scene);

    void deregister(Group scene);
}
