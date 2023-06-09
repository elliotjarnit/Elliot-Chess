package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Objects.ECamera;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;
import dev.elliotjarnit.ElliotEngine.Window.InputManager;

public class Main extends ElliotEngine {
    private boolean freeMove = false;
    private ECamera camera;

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
        this.inputManager.takeoverMouse();

        EScene mainScene = new EScene();
        camera = new ECamera(new Vector3(-88, 65, -52));
        camera.setRotationDegrees(new Vector2(-34.2, 62.5));
        Board gameBoard = new Board();
        mainScene.addObject(gameBoard);
        for (Piece piece : gameBoard.getPieces()) {
            mainScene.addObject(piece);
        }
        mainScene.setCamera(camera);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {
        if (freeMove) {
            if (this.inputManager.isKeyDown(InputManager.Key.W)) {
                camera.moveForward(0.5);
            }

            if (this.inputManager.isKeyDown(InputManager.Key.S)) {
                camera.moveForward(-0.5);
            }
            if (this.inputManager.isKeyDown(InputManager.Key.A)) {
                camera.moveRight(-0.5);
            }

            if (this.inputManager.isKeyDown(InputManager.Key.D)) {
                camera.moveRight(0.5);
            }

            if (this.inputManager.isKeyDown(InputManager.Key.SPACE)) {
                camera.setOrigin(new Vector3(camera.getOrigin().x, camera.getOrigin().y + 0.5, camera.getOrigin().z));
            }

            if (this.inputManager.isKeyDown(InputManager.Key.SHIFT)) {
                camera.setOrigin(new Vector3(camera.getOrigin().x, camera.getOrigin().y - 0.5, camera.getOrigin().z));
            }

            if (this.inputManager.isKeyDown(InputManager.Key.P)) {
                System.out.println("Origin: " + camera.getOrigin());
                System.out.println("Rotation: " + camera.getRotationDegrees());
            }

            // Mouse movement
            Vector2 mouseDelta = this.inputManager.getMouseDelta();

            camera.setRotationDegrees(camera.getRotationDegrees().add(new Vector2(mouseDelta.y * 0.3, mouseDelta.x * 0.3)));
        }
    }
}