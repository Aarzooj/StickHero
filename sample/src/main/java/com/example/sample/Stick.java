package com.example.sample;

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

    public void setLength(float length) {
        this.length = length;
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
