package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

import java.io.FileNotFoundException;

public class BoardSquare extends EObject {
    public BoardSquare(Vector3 boardOrigin, Vector2 boardPosition) {
        super();
        Vector3 position = new Vector3(boardOrigin);

        Vector2 NormalizedBoardPosition = new Vector2(boardPosition.x / 7, boardPosition.y / 7);

        NormalizedBoardPosition.x = (NormalizedBoardPosition.x * 2) - 1;
        NormalizedBoardPosition.y = (NormalizedBoardPosition.y * 2) - 1;

        NormalizedBoardPosition.x *= 60;
        NormalizedBoardPosition.y *= 60;

        this.setOrigin(new Vector3(NormalizedBoardPosition.x, 10, NormalizedBoardPosition.y));

        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/ChessBoardSquare.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            for (EFace face : faces) {
                // Alternate between black and white
                if (boardPosition.x % 2 == 0) {
                    if (boardPosition.y % 2 == 0) {
                        face.setColor(Color.WHITE);
                    } else {
                        face.setColor(Color.BLACK);
                    }
                } else {
                    if (boardPosition.y % 2 == 0) {
                        face.setColor(Color.BLACK);
                    } else {
                        face.setColor(Color.WHITE);
                    }
                }
            }
            this.setFaces(faces);
        } catch (FileNotFoundException | ObjHandler.NotTriangleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }
}
