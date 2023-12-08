package com.example.sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PauseMenu extends SideMenu{

    private static Stage stage;
    private static Scene scene;
    private static PauseMenu gen = null;

    private PauseMenu(){}

    // Singleton Design Pattern
    public static PauseMenu getInstance(){
        if (gen == null){
            gen = new PauseMenu();
        }
        return gen;
    }
    @Override
    public void returntohome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void savegame() {

    }

    @Override
    public void restart() {

    }
}
