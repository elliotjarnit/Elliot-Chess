package dev.elliotjarnit.elliotchess;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Objects.ECamera;
import dev.elliotjarnit.elliotengine.Objects.EObject;
import dev.elliotjarnit.elliotengine.Objects.EScene;
import dev.elliotjarnit.elliotengine.Overlay.EOButton;
import dev.elliotjarnit.elliotengine.Overlay.EOText;
import dev.elliotjarnit.elliotengine.Overlay.EOverlay;
import dev.elliotjarnit.elliotengine.Utils.Vector2;
import dev.elliotjarnit.elliotengine.Utils.Vector3;
import dev.elliotjarnit.elliotengine.Window.InputManager;

public class Main extends ElliotEngine {
    private boolean freeMove = false;
    private EScene mainScene;
    private ECamera currentCamera;
    private ECamera whiteCamera;
    private ECamera blackCamera;
    private Board gameBoard;
    private Piece selectedPiece;
    private boolean playing = false;
    private EOText title;
    private EOButton playButton;
    private EOverlay gameOverlay;
    private EOText turnText;
    private EOText fpsCounter;
    private Piece.Side turn = Piece.Side.WHITE;

    public static void main(String[] args) {
        System.out.println("TEST");
        Main engine = new Main();
        engine.run();
    }

    @Override
    public void optionSetup() {
        this.setOption(Options.NAME, "Elliot Chess");
        this.setOption(Options.WINDOW_WIDTH, "800");
        this.setOption(Options.WINDOW_HEIGHT, "800");
        this.setOption(Options.DESCRIPTION, "Chess game made with elliotengine");
        this.setOption(Options.VERSION, "1.0.0");
        this.setOption(Options.AUTHOR, "Elliot Jarnit, Jeremy Rowell");
        this.setOption(Options.LICENSE, "None");
        // Check if running from jar
        if (this.getClass().getResource("Main.class").toString().startsWith("jar")) {
            this.setOption(Options.LOADING_SCREEN, "true");
        } else {
            this.setOption(Options.LOADING_SCREEN, "false");
        }
    }

    @Override
    public void setup() {
        if (playing) {
            setupGame();
        } else {
            // Main Menu
            fpsCounter = new EOText(new Vector2(25, 14), "FPS: ", 14, EColor.WHITE);
            EScene mainMenuScene = new EScene(false);
            mainMenuScene.setSkyColor(EColor.BLACK);
            Skeleton skeleton1 = new Skeleton(new Vector3(6,  -10, 10));
            Skeleton skeleton2 = new Skeleton(new Vector3(-6,  -10, 10));
            ECamera camera = new ECamera(new Vector3(0, 0, 0));
            mainMenuScene.setCamera(camera);
            mainMenuScene.addObject(skeleton1);
            mainMenuScene.addObject(skeleton2);
            this.setScene(mainMenuScene);

            EOverlay mainMenuOverlay = new EOverlay();
            Vector2 windowSize = this.windowManager.getWindowSize();
            title = new EOText(new Vector2(windowSize.x / 2, windowSize.y / 4), "Elliot Chess", 30, EColor.WHITE);
            playButton = new EOButton(new Vector2(windowSize.x / 2, windowSize.y / 2 - 15), "Start Game", 20, 20, EColor.WHITE, EColor.BLACK);
            playButton.addListener(new EOButton.ClickListener() {
                @Override
                public void onClick() {
                    setOverlay(null);
                    playing = true;
                    setupGame();
                }
            });
            mainMenuOverlay.addComponent(title);
            mainMenuOverlay.addComponent(playButton);
            mainMenuOverlay.addComponent(fpsCounter);
            this.setOverlay(mainMenuOverlay);

            // Main Scene
            mainScene = new EScene(false);
            mainScene.setSkyColor(EColor.LIGHT_BLUE);
            Table table = new Table(new Vector3(0, -0.5, 0));
            Lamp lamp = new Lamp(new Vector3(0, 12, 150));
            mainScene.addObject(table);
            mainScene.addObject(lamp);

            // Game Board
            this.gameBoard = new Board();
            for (BoardSquare square : this.gameBoard.getBoardSquares()) {
                mainScene.addObject(square);
            }
            for (Piece piece : this.gameBoard.getPieces()) {
                mainScene.addObject(piece);
            }

            whiteCamera = new ECamera(new Vector3(-151, 124, -1));
            whiteCamera.setRotationDegrees(new Vector2(-43, 91));
            blackCamera = new ECamera(new Vector3(151, 124, -1));
            blackCamera.setRotationDegrees(new Vector2(-43, 269));
            currentCamera = blackCamera;

            mainScene.addObject(this.gameBoard);
            mainScene.setCamera(currentCamera);

            // Game Overlay
            gameOverlay = new EOverlay();
            turnText = new EOText(new Vector2(windowSize.x / 2, 50), "White's Turn", 20, EColor.WHITE, EColor.BLACK);
            gameOverlay.addComponent(turnText);
            gameOverlay.addComponent(fpsCounter);
        }
    }

    public void setupGame() {
        if (freeMove) this.inputManager.takeoverMouse();

        this.setOverlay(gameOverlay);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {
        fpsCounter.setText("FPS: " + this.renderer.getFPS());

        if (title != null) {
            title.setPosition(new Vector2(this.windowManager.getWindowSize().x / 2, this.windowManager.getWindowSize().y / 4));
            playButton.setPosition(new Vector2(this.windowManager.getWindowSize().x / 2, this.windowManager.getWindowSize().y / 2 - 15));
        }

        if (turnText != null) {
            if (turn == Piece.Side.WHITE) {
                turnText.setText("White's Turn");
            } else {
                turnText.setText("Black's Turn");
            }
        }

        if (playing) {
            if (turn == Piece.Side.WHITE) {
                currentCamera = whiteCamera;
                mainScene.setCamera(currentCamera);
            } else {
                currentCamera = blackCamera;
                mainScene.setCamera(currentCamera);
            }
        }

        if (freeMove && playing) {
            if (this.inputManager.isKeyDown(InputManager.Key.W)) {
                currentCamera.moveForward(2);
            }

            if (this.inputManager.isKeyDown(InputManager.Key.S)) {
                currentCamera.moveForward(-2);
            }
            if (this.inputManager.isKeyDown(InputManager.Key.A)) {
                currentCamera.moveRight(-2);
            }

            if (this.inputManager.isKeyDown(InputManager.Key.D)) {
                currentCamera.moveRight(2);
            }

            if (this.inputManager.isKeyDown(InputManager.Key.SPACE)) {
                currentCamera.setOrigin(new Vector3(currentCamera.getOrigin().x, currentCamera.getOrigin().y + 2, currentCamera.getOrigin().z));
            }

            if (this.inputManager.isKeyDown(InputManager.Key.SHIFT)) {
                currentCamera.setOrigin(new Vector3(currentCamera.getOrigin().x, currentCamera.getOrigin().y - 2, currentCamera.getOrigin().z));
            }

            if (this.inputManager.isKeyDown(InputManager.Key.P)) {
                System.out.println("Origin: " + currentCamera.getOrigin());
                System.out.println("Rotation: " + currentCamera.getRotationDegrees());
            }

            // Mouse movement
            Vector2 mouseDelta = this.inputManager.getMouseDelta();

            currentCamera.setRotationDegrees(currentCamera.getRotationDegrees().add(new Vector2(mouseDelta.y * 0.3, mouseDelta.x * 0.3)));


        }

        if (this.inputManager.isMouseDown(InputManager.MouseButton.LEFT)) {
            Vector2 mousePos = this.inputManager.getMousePos();
            EObject object = this.renderer.getObjectAtPoint(mousePos);
//            EObject object = null;

            if (object instanceof BoardSquare) {
                if (selectedPiece != null) {
                    BoardSquare square = (BoardSquare) object;
                    try {
                        gameBoard.movePiece(new Vector2(selectedPiece.getX(), selectedPiece.getY()), new Vector2(square.getBoardPosition().x, square.getBoardPosition().y));
                        selectedPiece.setColor(selectedPiece.getSide() == Piece.Side.WHITE ? EColor.WHITE : EColor.BLACK);
                        selectedPiece = null;
                        turn = turn == Piece.Side.WHITE ? Piece.Side.BLACK : Piece.Side.WHITE;
                        setAvailableMoves();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                if (selectedPiece != null) selectedPiece.setColor(selectedPiece.getSide() == Piece.Side.WHITE ? EColor.WHITE : EColor.BLACK);
                selectedPiece = null;
                setAvailableMoves();
                if (object instanceof Piece) {
                    if (((Piece) object).getSide() != turn) return;
                    selectedPiece = (Piece) object;
                    selectedPiece.setColor(EColor.RED);
                    setAvailableMoves();
                }
            }
        }
    }

    public void setAvailableMoves() {
        for (BoardSquare square : gameBoard.getBoardSquares()) {
            square.setDefaultColor();
        }

        BoardSquare[] squares = gameBoard.getBoardSquares();
        if (selectedPiece == null) return;
        for (BoardSquare square : squares) {
            try {
                if (selectedPiece.isValidMove(new Vector2(selectedPiece.getX(), selectedPiece.getY()), new Vector2(square.getBoardPosition().x, square.getBoardPosition().y), gameBoard)) {
                    square.setColor(EColor.RED);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}