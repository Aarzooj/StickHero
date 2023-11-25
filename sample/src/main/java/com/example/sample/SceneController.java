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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private Stage stage;
    private Scene scene;
    @FXML
    private Line stickLine;

    private Timeline timeline;
    @FXML
    private ImageView myHero;

    public void switchToPlayScreen(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainscreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        // Initialize the Timeline
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
        rotateStickLine();
    }
    private void rotateStickLine() {
        double deltaX = stickLine.getEndX() - stickLine.getStartX();
        double deltaY = stickLine.getEndY() - stickLine.getStartY();
        double angle = Math.atan2(deltaY, deltaX);

        // Convert the angle from radians to degrees and negate it to rotate to the right
        double degrees = -Math.toDegrees(angle);

        // Apply rotation transformation gradually
        Timeline rotationTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> rotateStick(degrees / 100))
        );
        rotationTimeline.setCycleCount(100);
        rotationTimeline.setOnFinished(e -> moveHero());
        rotationTimeline.play();
    }
    private void moveHero() {
        // Move the hero after the stick rotation is complete
        double stickLength = Math.sqrt(Math.pow(stickLine.getEndX()-stickLine.getStartX(),2) + Math.pow(stickLine.getEndY()-stickLine.getStartY(),2));
        double targetX = myHero.getX() + stickLength + 15;
        double currentX = myHero.getX();

        // Calculate the duration based on the distance to move and the speed
        double distanceToMove = Math.abs(targetX - currentX);
        double speed = 1.0;

        // Use a Timeline to gradually change the hero's X position
        Timeline moveTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    double newX = myHero.getX() + (targetX > currentX ? speed : -speed);
                    myHero.setX(newX);
                })
        );

        moveTimeline.setOnFinished(event -> fall());

        // Calculate the number of cycles based on the distance and speed
        int cycles = (int) (distanceToMove / speed);
        moveTimeline.setCycleCount(cycles);
        moveTimeline.play();

    }
    private void rotateStick(double angle) {
        Rotate rotate = new Rotate(angle, stickLine.getStartX(), stickLine.getStartY());
        stickLine.getTransforms().add(rotate);
    }
    public void increaseStickLength(ActionEvent event){
        stickLine.setEndY(stickLine.getEndY() - 2);
    }

    public void fall() {
        // Set up falling parameters
        double fallDistance = 300.0; // You can adjust this value based on how far you want the hero to fall
        double speed = 1.0;

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
}