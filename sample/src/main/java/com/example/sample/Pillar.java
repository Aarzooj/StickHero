package com.example.sample;

public class Pillar {
    private float width;
    private float redCentre;
    private float distanceFromPrev;
    private static float length = 10;

    public Pillar(float width, float redCentre, float distanceFromPrev) {
        this.width = width;
        this.redCentre = redCentre;
        this.distanceFromPrev = distanceFromPrev;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getRedCentre() {
        return redCentre;
    }

    public void setRedCentre(float redCentre) {
        this.redCentre = redCentre;
    }

    public float getDistanceFromPrev() {
        return distanceFromPrev;
    }

    public void setDistanceFromPrev(float distanceFromPrev) {
        this.distanceFromPrev = distanceFromPrev;
    }

    public static float getLength() {
        return length;
    }
}
