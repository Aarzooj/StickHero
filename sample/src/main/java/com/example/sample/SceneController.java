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
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
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
    private Stage stage;
    private Scene scene;

    public static int stickno = 0;
    public static int pillarno = 0;

    @FXML
    private Rectangle prevPillar;

    private Pillar initialPillar;

    public static ArrayList<Pillar> pillars = new ArrayList<>();
    public static ArrayList<Rectangle> rectangles = new ArrayList<>();
    public static ArrayList<Stick> sticks = new ArrayList<>();
    public static ArrayList<Line> sticklines = new ArrayList<>();


    public void switchToPlayScreen(ActionEvent event) throws IOException{
        MainMenu.play(event);
    }

    public void switchToPauseScreen(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pause.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        // Initialize the Timeline
        hero = new Hero(1,1.0,0,0);
        stick = new Stick(0,0);
        sticks.add(stick);
        sticklines.add(0,stickLine);

//        double width = nextPillar.getWidth();
//        double prevDistance = nextPillar.getX() - (prevPillar.getX() + prevPillar.getWidth());
//        targetPillar = new Pillar(width, width/2, prevDistance);
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(10), this::increaseStickLength));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void handleMousePressed(MouseEvent event) {
        sticklines.get(stickno).setOpacity(1);
        timeline.play();
    }

    @FXML
    public void handleMouseReleased(MouseEvent event) throws InterruptedException {
        // Stop the Timeline when the mouse is released
        timeline.stop();
        if (pillarno == 0){
            rectangles.add(prevPillar);
            rectangles.add(nextPillar);
            double width = nextPillar.getWidth();
            double prevDistance = nextPillar.getLayoutX() - (prevPillar.getLayoutX() + prevPillar.getWidth());
            targetPillar = new Pillar(width, width/2, prevDistance);
            pillars.add(initialPillar);
            pillars.add(targetPillar);
        }

        double stickLength = Math.sqrt(Math.pow(sticklines.get(stickno).getEndX()-sticklines.get(stickno).getStartX(),2) + Math.pow(sticklines.get(stickno).getEndY()-sticklines.get(stickno).getStartY(),2));
        sticks.get(stickno).setLength(stickLength);
        sticks.get(stickno).rotateStick(sticklines.get(stickno),hero,myHero,pillars.get(pillarno+1),rectangles.get(pillarno+1),rectangles.get(pillarno));
    }

    public void increaseStickLength(ActionEvent event){
        //System.out.println(stickno);
        sticks.get(stickno).increaseLength(sticklines.get(stickno));
    }
}