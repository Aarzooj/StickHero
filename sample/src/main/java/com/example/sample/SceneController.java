package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
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
    private Button scoreButton;

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private ImageView myHero;

    @FXML
    private Rectangle nextPillar;

    private int stickDown = 0;

    @FXML
    private Button cherryCount;

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
    public static ArrayList<ImageView> cherries = new ArrayList<>();


    public void switchToPlayScreen(ActionEvent event) throws IOException{
        stickno = 0;
        pillarno = 0;
        sticks.clear();
        sticklines.clear();
        rectangles.clear();
        cherries.clear();
        pillars.clear();
        stickDown = 0;
        MainMenu.play(event);
    }

    public void increaseCircle(MouseEvent event){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle1);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        // Play the scale animation
        scaleTransition.play();
    };

    public void increaseCircle1(MouseEvent event){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle2);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        // Play the scale animation
        scaleTransition.play();
    };

    public void decreaseCircle(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle1);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        // Play the reverse scale animation
        scaleTransition.play();
    }

    public void decreaseCircle1(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle2);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        // Play the reverse scale animation
        scaleTransition.play();
    }

    public void restartGame(MouseEvent event) throws IOException{
        stickno = 0;
        pillarno = 0;
        sticks.clear();
        sticklines.clear();
        rectangles.clear();
        cherries.clear();
        pillars.clear();
        stickDown = 0;
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainMenu.class.getResource("mainscreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToHomeScreen(MouseEvent event) throws IOException{
        PauseMenu pause = new PauseMenu();
        pause.returntohome(event);
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

        floatHeroImage();
//        double width = nextPillar.getWidth();
//        double prevDistance = nextPillar.getX() - (prevPillar.getX() + prevPillar.getWidth());
//        targetPillar = new Pillar(width, width/2, prevDistance);
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(10), this::increaseStickLength));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void handleMousePressed(MouseEvent event) {
        if (sticks.get(stickno).getRotation() == 0){
            sticklines.get(stickno).setOpacity(1);
            stickDown = 1;
            timeline.play();
        }else if (sticks.get(stickno).getRotation() == -90){
            stickDown = 0;
            hero.flipHero(myHero);
        }
    }

    public void floatHeroImage() {
        // Create a TranslateTransition for the hero image
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), myHero);
        translateTransition.setByY(2); // Adjust the value based on your preference
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        // Start the floating animation
        translateTransition.play();
    }

    @FXML
    public void handleMouseReleased(MouseEvent event) throws InterruptedException {
        // Stop the Timeline when the mouse is released
        if (stickDown == 0){
            return;
        }
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
        sticks.get(stickno).rotateStick(sticklines.get(stickno),hero,myHero,pillars.get(pillarno+1),rectangles.get(pillarno+1),rectangles.get(pillarno),scoreButton,cherryCount);
    }

    public void increaseStickLength(ActionEvent event){
        //System.out.println(stickno);
        sticks.get(stickno).increaseLength(sticklines.get(stickno));
    }
}