package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Stick {
    private double length;
    private float rotation;

    public Stick(float length, float rotation) {
        this.length = length;
        this.rotation = rotation;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length){
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

    public void rotateStick(Line stickLine, Hero hero, ImageView myHero, Pillar targetPillar, Rectangle target, Rectangle prevPillar, Button scoreButton, Button cherryCount){
        double deltaX = stickLine.getEndX() - stickLine.getStartX();
        double deltaY = stickLine.getEndY() - stickLine.getStartY();
        double angle = Math.atan2(deltaY, deltaX);

        // Convert the angle from radians to degrees and negate it to rotate to the right
        double degrees = -Math.toDegrees(angle);

        // Apply rotation transformation gradually
        Timeline rotationTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> rotateStick(degrees / 100,stickLine))
        );
        rotationTimeline.setCycleCount(100);
        rotationTimeline.setOnFinished(e -> hero.move(myHero,stickLine,targetPillar,target,prevPillar,scoreButton,cherryCount));
        rotationTimeline.play();
    }
    private void rotateStick(double angle, Line stickLine) {
        Rotate rotate = new Rotate(angle, stickLine.getStartX(), stickLine.getStartY());
        stickLine.getTransforms().add(rotate);
        this.setRotation(-90);
    }
    public boolean hitRedCentre(Pillar p){

        return false;
    }
}
