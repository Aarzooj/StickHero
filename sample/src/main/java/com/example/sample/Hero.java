package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Hero {
    private int state;
    private double speed;
    private int cherries;
    private int score;
    private Stick stick;

    public void collectCherries(int cherries) {
        this.cherries = cherries;
    }

    public Hero(int state, double speed, int cherries, int score) {
        this.state = state;
        this.speed = speed;
        this.cherries = cherries;
        this.score = score;
        this.stick = new Stick(0,90);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCherries() {
        return cherries;
    }

    public void dropStick(){
    }

    public void fall(ImageView myHero){
        double fallDistance = 300.0; // You can adjust this value based on how far you want the hero to fall
        double speed = 2.0;

        // Calculate the target Y position for falling
        double targetY = myHero.getY() + fallDistance;

        // Calculate the duration based on the distance to fall and the speed
        double distanceToFall = Math.abs(targetY - myHero.getY());

        // Use a Timeline to gradually change the hero's Y position
        Timeline fallTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    double newY = myHero.getY() + speed;
                    myHero.setY(newY);
                })
        );

        // Calculate the number of cycles based on the distance and speed
        int cycles = (int) (distanceToFall / speed);
        fallTimeline.setCycleCount(cycles);
        fallTimeline.play();
    }

    public void revive(){
    }

    public void move(ImageView myHero, Line stickLine, Pillar targetPillar){
        double stickLength = Math.sqrt(Math.pow(stickLine.getEndX()-stickLine.getStartX(),2) + Math.pow(stickLine.getEndY()-stickLine.getStartY(),2));
        double targetX = myHero.getX() + stickLength + 15;
        double currentX = myHero.getX();

        // Calculate the duration based on the distance to move and the speed
        double distanceToMove = Math.abs(targetX - currentX);

        // Use a Timeline to gradually change the hero's X position
        Timeline moveTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    double newX = myHero.getX() + (targetX > currentX ? speed : -speed);
                    myHero.setX(newX);
                })
        );

        System.out.println(stickLength);
        System.out.println(targetPillar.getDistanceFromPrev() );

        if (stickLength < targetPillar.getDistanceFromPrev() || stickLength > (targetPillar.getDistanceFromPrev() + targetPillar.getWidth())){
            System.out.println("Yes");
            moveTimeline.setOnFinished(event -> fall(myHero));

            // Calculate the number of cycles based on the distance and speed
            int cycles = (int) (distanceToMove / this.speed);
            moveTimeline.setCycleCount(cycles);
            moveTimeline.play();
        }
        else{
            double extramove = Math.abs(targetPillar.getWidth() - (stickLength - targetPillar.getDistanceFromPrev()));
            // Calculate the number of cycles based on the distance and speed
            int cycles = (int) ((distanceToMove + extramove) / this.speed);
            moveTimeline.setCycleCount(cycles);
            moveTimeline.play();
        }
        
    }

    public boolean hitRedCentre(Pillar p){

        return false;
    }
}
