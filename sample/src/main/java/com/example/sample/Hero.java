package com.example.sample;

public class Hero {
    private String state;
    private double speed;
    private int cherries;
    private int score;
    private Stick stick;

    public void collectCherries(int cherries) {
        this.cherries = cherries;
    }

    public Hero(String state, double speed, int cherries, int score) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public void fall(){
    }

    public void revive(){
    }

    public void move(){
    }

    public boolean hitRedCentre(Pillar p){

        return false;
    }
}
