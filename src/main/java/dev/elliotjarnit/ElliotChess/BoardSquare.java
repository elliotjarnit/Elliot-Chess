package main.java.dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

import java.io.FileNotFoundException;

public class BoardSquare extends EObject {
    private Vector2 boardPosition;

    public BoardSquare(Vector3 boardOrigin, Vector2 boardPosition) {
        super();
        this.boardPosition = boardPosition;

        Vector3 position = new Vector3(boardOrigin);

        Vector2 NormalizedBoardPosition = new Vector2(boardPosition.x / 7, boardPosition.y / 7);

        NormalizedBoardPosition.x = (NormalizedBoardPosition.x * 2) - 1;
        NormalizedBoardPosition.y = (NormalizedBoardPosition.y * 2) - 1;

        NormalizedBoardPosition.x *= 60;
        NormalizedBoardPosition.y *= 60;

        position.x += NormalizedBoardPosition.x;
        position.z += NormalizedBoardPosition.y;

        this.setOrigin(new Vector3(NormalizedBoardPosition.x, 10, NormalizedBoardPosition.y));

        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/ChessBoardSquare.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            this.setFaces(faces);
        } catch (FileNotFoundException | ObjHandler.NotTriangleException e) {
            e.printStackTrace();
        }

        this.setDefaultColor();
    }

    @Override
    public void update() {

    }

    public Vector2 getBoardPosition() {
        return boardPosition;
    }

    public void setDefaultColor() {
        // Slighty off-white and off-black so that the pieces are more visible
        EColor white = new EColor(240, 225, 166);
        EColor black = new EColor(50, 50, 50);

        // Alternate between black and white
        if (boardPosition.x % 2 == 0) {
            if (boardPosition.y % 2 == 0) {
                this.setColor(white);
            } else {
                this.setColor(black);
            }
        } else {
            if (boardPosition.y % 2 == 0) {
                this.setColor(black);
            } else {
                this.setColor(white);
            }
        }
    }
}
