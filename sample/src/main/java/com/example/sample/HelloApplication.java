package com.example.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static ImageView getImageView() {
        Image backgroundImage = new Image("C:\\Users\\anush\\Desktop\\sample\\src\\main\\java\\com\\example\\sample\\assets\\bg2.png"); // Replace with the actual path
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Get the dimensions of the screen
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        // Set the dimensions of the ImageView to match the screen
        backgroundImageView.setFitWidth(screenBounds.getWidth());
        backgroundImageView.setFitHeight(screenBounds.getHeight());
        return backgroundImageView;
    }

    private Button createPlayButton() {
        // Create a circular play button with red color
        Button playButton = new Button("PLAY");

        Circle circle = new Circle(100); // Set the radius of the circle
        circle.setFill(Color.RED);

        // Set the font for the button text
        playButton.setFont(new Font("Arial", 20));

        // Set the shape of the button to the circular play button
        playButton.setShape(circle);

        // Set the background of the button to null to make it transparent
//        playButton.setBackground(null);
        playButton.setStyle("-fx-background-color: red;");
        // Set the button text color to white for better visibility
        playButton.setTextFill(Color.WHITE);

        // Center the text within the button
        playButton.setAlignment(Pos.CENTER);
        // Set the event handler for the button (add your play functionality here)
        playButton.setOnAction(e -> {
            System.out.println("Play button clicked!");
            // Add your play functionality here
        });

        return playButton;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Stick Hero");
        stage.setFullScreen(true);
        ImageView backgroundImageView = getImageView();
        Text title = new Text("STICK HERO");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD,100));
        title.setTranslateY(75);
        StackPane root = new StackPane();
        root.getChildren().add(backgroundImageView);
        root.getChildren().add(title);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        Button playButton = createPlayButton();
        root.getChildren().add(playButton);
        Scene scene = new Scene(root,400,600);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}