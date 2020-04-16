package com.pracownia.vanet.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class MapScheme {
    public enum Range{
        ON(Color.BLACK),
        OFF(Color.TRANSPARENT);

        private Color color;

        Range(Color color) {
            this.color = color;
        }

        public Paint color() {
            return color;
        }
    }

}
