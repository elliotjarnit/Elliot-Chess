package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;

public class Main extends ElliotEngine {
    public static void main(String[] args) {
        Main engine = new Main();
        engine.run();
    }

    @Override
    public void optionSetup() {
        this.setOption(Options.NAME, "Elliot Chess");
        this.setOption(Options.WINDOW_WIDTH, "800");
        this.setOption(Options.WINDOW_HEIGHT, "800");
        this.setOption(Options.DESCRIPTION, "Chess game made with ElliotEngine");
        this.setOption(Options.VERSION, "1.0.0");
        this.setOption(Options.AUTHOR, "Elliot Jarnit, Jeremy Rowell");
        this.setOption(Options.LICENSE, "None");
    }

    @Override
    public void setup() {
        EScene mainScene = new EScene();
        Board gameBoard = new Board();
        mainScene.addObject(gameBoard);
        for (Piece piece : gameBoard.getPieces()) {
            mainScene.addObject(piece);
        }
        this.setScene(mainScene);
    }

    @Override
    public void loop() {

    }
}