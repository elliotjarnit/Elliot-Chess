package dev.elliotjarnit.elliotchess;
import dev.elliotjarnit.elliotengine.Exceptions.NotTriangleException;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Handlers.FileHandler;
import dev.elliotjarnit.elliotengine.Handlers.ObjHandler;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Objects.EObject;
import dev.elliotjarnit.elliotengine.Utils.Vector2;
import dev.elliotjarnit.elliotengine.Utils.Vector3;

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
        } catch (FileNotFoundException | NotTriangleException e) {
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
