package com.example.sample;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public abstract class SideMenu extends Menu{

    public abstract void returntohome(MouseEvent event) throws IOException;
    public abstract void savegame();
    public abstract void restart();

}
