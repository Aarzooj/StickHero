package com.example.sample;

import javafx.scene.shape.Line;

public class Stick {
    private float length;
    private float rotation;

    public Stick(float length, float rotation) {
        this.length = length;
        this.rotation = rotation;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length){
        this.length = length;
    }

    public void increaseLength(Line stickLine) {
        stickLine.setEndY(stickLine.getEndY() - 2);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public boolean hitRedCentre(Pillar p){

        return false;
    }
}
