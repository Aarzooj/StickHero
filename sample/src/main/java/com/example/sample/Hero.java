package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Hero {
    private int state;
    private double speed;
    private int cherries;
    private int score;
    private Stick stick;
    private int cherriesCollected = 0;
    final Timeline[] moveTimeline = new Timeline[1];
    public TranslateTransition cherryExit,shiftTransition,heroTransition,stickTransition,prevTransition,transition,cherryTransition;
    Timeline fallTimeline;

    public void collectCherries(int cherries) {
        this.cherries = cherries;
    }

    public Hero(int state, double speed, int cherries, int score) {
        this.state = state;
        this.speed = speed;
        this.cherries = cherries;
        this.score = score;
        this.stick = new Stick(0, 90);
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

    public void dropStick() {
    }

    public void flipHero(ImageView myHero) {
        if (this.state == 1) {
            double originalBottomY = myHero.getFitHeight();
            myHero.setScaleY(myHero.getScaleY() * -1);
            myHero.setLayoutY(myHero.getLayoutY() + originalBottomY);
            this.state = -1;
        } else if (this.state == -1) {
            double originalBottomY = myHero.getFitHeight();
            myHero.setScaleY(myHero.getScaleY() * -1);
            myHero.setLayoutY(myHero.getLayoutY() - originalBottomY);
            this.state = 1;
        }
    }

    public void fall(ImageView myHero) throws IOException {
        double fallDistance = 300.0; // You can adjust this value based on how far you want the hero to fall
        double speed = 2.0;

        // Calculate the target Y position for falling
        double targetY = myHero.getY() + fallDistance;

        // Calculate the duration based on the distance to fall and the speed
        double distanceToFall = Math.abs(targetY - myHero.getY());

        // Use a Timeline to gradually change the hero's Y position
        fallTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    double newY = myHero.getY() + speed;
                    myHero.setY(newY);
                })
        );
        int c;
//        int finalC = this.score;
        FileInputStream in = null;
        try{
            in = new FileInputStream("high_score.txt");
            c = Math.max(in.read(),0);
            if (this.score > c){
                FileOutputStream out = null;
                try {
                    System.out.println(c);
                    System.out.println(this.score);
                    out = new FileOutputStream("high_score.txt");
//                    out.write("".getBytes());
                    out.write(this.score);
//                    finalC = c;
                    c = this.score;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    out.close();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            in.close();
        }
        int finalC1 = c;
        //System.out.println(finalC1);
        fallTimeline.setOnFinished(event -> {
            FileOutputStream saveScore = null;
            try{
                saveScore = new FileOutputStream("score.txt");
                saveScore.write(this.score);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int total_cherries;
            FileInputStream in1 = null;
            try {
                in1 = new FileInputStream("cherry.txt");
                int a = Math.max(in1.read(),0);

                System.out.println("a"+a);
                total_cherries = a + this.cherries;
                FileOutputStream out1 = null;
                out1 = new FileOutputStream("cherry.txt");
                out1.write(total_cherries);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Load the gameover.fxml scene
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("gameover.fxml"));
                Parent gameoverRoot = loader.load();
                Scene gameoverScene = new Scene(gameoverRoot);
                Label scoreLabel = (Label) gameoverRoot.lookup("#scoreLabel");
                Label highLabel = (Label) gameoverRoot.lookup("#highLabel");
                Button cherryCount = (Button) gameoverRoot.lookup("#cherryCount");

                // Set the values
                scoreLabel.setText(String.valueOf(score));
                highLabel.setText(String.valueOf(finalC1));
                cherryCount.setText(String.valueOf(total_cherries));
                // Get the current stage
                Stage currentStage = (Stage) myHero.getScene().getWindow();

                // Set the gameover scene and show it
                currentStage.setScene(gameoverScene);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Calculate the number of cycles based on the distance and speed
        int cycles = (int) (distanceToFall / speed);
        fallTimeline.setCycleCount(cycles);
        fallTimeline.play();
    }

    public void revive() {
    }

    public void move(ImageView myHero, Line stickLine, Pillar targetPillar, Rectangle target, Rectangle prevPillar, Button scoreButton, Button cherryCount) {
        double stickLength = Math.sqrt(Math.pow(stickLine.getEndX() - stickLine.getStartX(), 2) + Math.pow(stickLine.getEndY() - stickLine.getStartY(), 2));
        double targetX = myHero.getX() + stickLength + 15;
        double currentX = myHero.getX();

        // Calculate the duration based on the distance to move and the speed
        double distanceToMove = Math.abs(targetX - currentX);

        // Use a Timeline to gradually change the hero's X position
        moveTimeline[0] = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    if (myHero.getBoundsInParent().intersects(target.getBoundsInParent()) && this.state == -1) {
                        try {
                            this.fall(myHero);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        moveTimeline[0].stop(); // Stop the timeline immediately
                    } else {
                        double newX = myHero.getX() + (targetX > currentX ? speed : -speed);
                        myHero.setX(newX);
                    }
                    if (!SceneController.cherries.isEmpty()){
                        ImageView collectedCherry = SceneController.cherries.get(SceneController.cherries.size() - 1);
                        if (this.state == -1 && myHero.getBoundsInParent().intersects(collectedCherry.getBoundsInParent())){
                            cherriesCollected = 1;
                            collectedCherry.setX(collectedCherry.getX() - (targetPillar.getDistanceFromPrev() + targetPillar.getWidth() + SceneController.rectangles.get(0).getWidth()));
                        }
                    }
                })
        );

//        System.out.println(stickLength);
//        System.out.println("New" + targetPillar.getDistanceFromPrev());

        if (stickLength < targetPillar.getDistanceFromPrev() || stickLength > (targetPillar.getDistanceFromPrev() + targetPillar.getWidth())) {
            moveTimeline[0].setOnFinished(event -> {
                try {
                    fall(myHero);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // Calculate the number of cycles based on the distance and speed
            int cycles = (int) (distanceToMove / this.speed);
            moveTimeline[0].setCycleCount(cycles);
            moveTimeline[0].play();
        } else {
            double extramove = Math.abs(targetPillar.getWidth() - (stickLength - targetPillar.getDistanceFromPrev())) - 15;
            // Calculate the number of cycles based on the distance and speed
            int cycles = (int) ((distanceToMove + extramove) / this.speed);
            moveTimeline[0].setCycleCount(cycles);
            moveTimeline[0].setOnFinished(event -> {
                scoreButton.setText(String.valueOf(Integer.parseInt(scoreButton.getText()) + 1));
                this.score += 1;
                if (cherriesCollected == 0 && !SceneController.cherries.isEmpty()){
                    ImageView uncollectedCherry = SceneController.cherries.get(SceneController.cherries.size() - 1);
                    cherryExit = new TranslateTransition(Duration.millis(500),uncollectedCherry);
                    double cherryDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth() + SceneController.rectangles.get(0).getWidth();
                    cherryExit.setByX(-cherryDistance);
                    cherryExit.play();
                }else if (cherriesCollected == 1){
                    cherryCount.setText(String.valueOf(Integer.parseInt(cherryCount.getText()) + 1));
                    this.cherries += 1;
                    cherriesCollected = 0;
                }
                // Target pillar old
                shiftTransition = new TranslateTransition(Duration.millis(500), target);
                // Calculate the translation distance
                double translationDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                // Set the translation
                shiftTransition.setByX(-translationDistance);

                // Play the translation animation
                shiftTransition.play();
                heroTransition = new TranslateTransition(Duration.millis(500), myHero);

                // Calculate the translation distance
                double heroDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                // Set the translation
                heroTransition.setByX(-heroDistance);

                // Play the translation animation
                heroTransition.play();

                stickTransition = new TranslateTransition(Duration.millis(500), stickLine);

                // Calculate the translation distance
                double stickDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                // Set the translation
                stickTransition.setByX(-stickDistance);

                // Play the translation animation
                stickTransition.play();
                prevTransition = new TranslateTransition(Duration.millis(500), prevPillar);

                // Calculate the translation distance
                // Base pillar
                double prevDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                // Set the translation
                prevTransition.setByX(-prevDistance);

                // Play the translation animation
                prevTransition.play();
                AnchorPane anchor = (AnchorPane) myHero.getScene().lookup("#anchor");

                // Check if anchor is not null before proceeding
                if (anchor != null) {
                    double width = 30 + Math.random() * 120;
                    Rectangle nextPillar = new Rectangle(width, 200);
//                    System.out.println(nextPillar.getWidth());
                    nextPillar.setLayoutX(anchor.getWidth());
                    nextPillar.setLayoutY(target.getLayoutY());
                    anchor.getChildren().add(nextPillar);
                    SceneController.rectangles.add(nextPillar);
                    SceneController.pillarno++;
                    transition = new TranslateTransition(Duration.millis(500), nextPillar);
                    double extra = 9 + Math.random() * 180;
                    SceneController.cherryGenerate = 0;
                    Random random = new Random();
                    // Generate a random number, either 0 or 1
                    int generateCherry = random.nextInt(2);
//                    System.out.printf("Extra: %f Flag: %d\n",extra,generateCherry);
                    if (extra > 50 && generateCherry == 1){
                        Image cherryImage = new Image("cherry.png");
                        ImageView cherry = new ImageView(cherryImage);
                        anchor.getChildren().add(cherry);
                        cherry.setFitHeight(25);
                        cherry.setFitWidth(27);
                        cherry.setLayoutX(anchor.getWidth());
                        cherry.setLayoutY(220);
                        double cherryExtra = 18 + Math.random() * (extra - 18);
//                        System.out.println("Cherry Extra: " + cherryExtra);
                        cherryTransition = new TranslateTransition(Duration.millis(500), cherry);
                        cherryTransition.setToX(-(anchor.getWidth() - (SceneController.rectangles.get(0).getWidth() + cherryExtra) + 25));
                        cherryTransition.play();
                        SceneController.cherries.add(cherry);
                        SceneController.cherryGenerate = 1;
                    }
                    transition.setToX(-(anchor.getWidth() - (SceneController.rectangles.get(0).getWidth() + extra)));
//                    System.out.println("Old" + extra);
                    Pillar newPillar = new Pillar(width, width / 2, extra);
                    SceneController.pillars.add(newPillar);
                    transition.play();
                    Stick stick = new Stick(0, 0);
                    Line line = new Line(SceneController.rectangles.get(0).getWidth() - 5, target.getLayoutY(), SceneController.rectangles.get(0).getWidth() - 5, target.getLayoutY() - 15);
                    line.setOpacity(0);
                    line.setStrokeWidth(4.0);
                    // anchor.getChildren().remove(stickLine);
                    anchor.getChildren().add(line);
                    SceneController.stickno++;
//                    System.out.println(SceneController.stickno);
                    SceneController.sticklines.add(SceneController.stickno, line);
                    SceneController.sticks.add(stick);

                } else {
                    // Handle the case where anchor is null
                    System.out.println("Anchor is null");
                }
            });
            moveTimeline[0].play();
        }
    }

    public boolean hitRedCentre(Pillar p) {

        return false;
    }
}
