package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    @FXML
    private Line stickLine;
    private Stick stick;
    private Hero hero;
    private Timeline timeline;

    @FXML
    private ImageView myHero;

    @FXML
    private Rectangle nextPillar;
    private Pillar targetPillar;

    @FXML
    private Rectangle prevPillar;

    public void switchToPlayScreen(ActionEvent event) throws IOException{
        MainMenu.play(event);
    }

    @FXML
    public void initialize() {
        // Initialize the Timeline
        hero = new Hero(1,1.0,0,0);
        stick = new Stick(0,0);

//        double width = nextPillar.getWidth();
//        double prevDistance = nextPillar.getX() - (prevPillar.getX() + prevPillar.getWidth());
//        targetPillar = new Pillar(width, width/2, prevDistance);
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(10), this::increaseStickLength));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void handleMousePressed(MouseEvent event) {
        stickLine.setOpacity(1);
        timeline.play();
    }

    @FXML
    public void handleMouseReleased(MouseEvent event) throws InterruptedException {
        // Stop the Timeline when the mouse is released
        timeline.stop();
        double width = nextPillar.getWidth();
        double prevDistance = nextPillar.getLayoutX() - (prevPillar.getLayoutX() + prevPillar.getWidth());
        targetPillar = new Pillar(width, width/2, prevDistance);
        double stickLength = Math.sqrt(Math.pow(stickLine.getEndX()-stickLine.getStartX(),2) + Math.pow(stickLine.getEndY()-stickLine.getStartY(),2));
        stick.setLength(stickLength);
        stick.rotateStick(stickLine,hero,myHero,targetPillar);
    }

    public void increaseStickLength(ActionEvent event){
        stick.increaseLength(stickLine);
    }
}