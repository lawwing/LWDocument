package com.lawwing.lwdocument.event;

/**
 * Created by lawwing on 2017/11/18.
 */

public class ColorAddEvent {
    private String color;

    public ColorAddEvent(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
