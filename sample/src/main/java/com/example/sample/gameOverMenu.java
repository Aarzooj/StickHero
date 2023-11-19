package com.example.sample;

public class gameOverMenu extends SideMenu{

    private gameOverScore scoreBoard;

    public gameOverMenu(gameOverScore scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @Override
    public void returntohome() {

    }

    @Override
    public void savegame() {

    }

    @Override
    public void restart() {

    }

    public void usecherriestorevive(){

    }

    public gameOverScore getscoreboard(){
        return this.scoreBoard;
    }
}
