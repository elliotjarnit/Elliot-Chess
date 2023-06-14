package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Objects.ECamera;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Overlay.EOButton;
import dev.elliotjarnit.ElliotEngine.Overlay.EOText;
import dev.elliotjarnit.ElliotEngine.Overlay.EOverlay;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;
import dev.elliotjarnit.ElliotEngine.Window.InputManager;

public class Main extends ElliotEngine {
    private boolean freeMove = false;
    private ECamera camera;
    private Board gameBoard;
    private Piece selectedPiece;
    private boolean playing = false;
    private EOText title;
    private EOButton playButton;
    private EOverlay gameOverlay;
    private EOText turnText;
    private Piece.Side turn = Piece.Side.WHITE;

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
        if (playing) {
            setupGame();
        } else {
            // Main Menu
            EScene mainMenuScene = new EScene(false);
            mainMenuScene.setSkyColor(EColor.BLACK);
            Skeleton skeleton1 = new Skeleton(new Vector3(6,  -10, 10));
            Skeleton skeleton2 = new Skeleton(new Vector3(-6,  -10, 10));
            camera = new ECamera(new Vector3(0, 0, 0));
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
            this.setOverlay(mainMenuOverlay);
        }
    }

    public void setupGame() {
        if (freeMove) this.inputManager.takeoverMouse();

        gameOverlay = new EOverlay();
        Vector2 windowSize = this.windowManager.getWindowSize();
        turnText = new EOText(new Vector2(windowSize.x / 2, 50), "White's Turn", 20, EColor.WHITE, EColor.BLACK);
        gameOverlay.addComponent(turnText);
        this.setOverlay(gameOverlay);

        EScene mainScene = new EScene();
        mainScene.setSkyColor(EColor.LIGHT_BLUE);
//        camera = new ECamera(new Vector3(-88, 65, -52));
//        camera.setRotationDegrees(new Vector2(-34.2, 62.5));
        camera = new ECamera(new Vector3(123, 102, -65));
        camera.setRotationDegrees(new Vector2(-41, 304));
        gameBoard = new Board();
        Table table = new Table(new Vector3(0, -0.5, 0));
        Lamp lamp = new Lamp(new Vector3(0, 0, 150));
        mainScene.addObject(gameBoard);
        mainScene.addObject(table);
        mainScene.addObject(lamp);
        for (BoardSquare square : gameBoard.getBoardSquares()) {
            mainScene.addObject(square);
        }
        for (Piece piece : gameBoard.getPieces()) {
            mainScene.addObject(piece);
        }
        mainScene.setCamera(camera);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {
        title.setPosition(new Vector2(this.windowManager.getWindowSize().x / 2, this.windowManager.getWindowSize().y / 4));
        playButton.setPosition(new Vector2(this.windowManager.getWindowSize().x / 2, this.windowManager.getWindowSize().y / 2 - 15));

        if (turnText != null) {
            if (turn == Piece.Side.WHITE) {
                turnText.setText("White's Turn");
            } else {
                turnText.setText("Black's Turn");
            }
        }

        if (freeMove && playing) {
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

        Vector2 mousePos = this.inputManager.getMousePos();

        if (this.inputManager.isMouseDown(InputManager.MouseButton.LEFT)) {
            EObject object = this.renderer.getLookingAtObject(mousePos);

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