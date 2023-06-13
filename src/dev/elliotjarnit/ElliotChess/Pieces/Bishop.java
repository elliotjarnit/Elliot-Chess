package dev.elliotjarnit.ElliotChess.Pieces;

import dev.elliotjarnit.ElliotChess.Board;
import dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

import java.io.FileNotFoundException;

public class Bishop extends Piece {
    public Bishop(Side side, Vector2 boardPosition) {
        super(side, boardPosition);

        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/bishop.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            for (EFace face : faces) {
                face.setColor(side == Side.WHITE ? Color.WHITE : Color.BLACK);
            }
            this.setFaces(faces);
        } catch (FileNotFoundException | ObjHandler.NotTriangleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        int dx = (int) Math.abs(endPos.x - startPos.x);
        int dy = (int) Math.abs(endPos.y - startPos.y);
        if (dy == dx) {
            int xDirection = Double.compare(startPos.x, endPos.x);
            int yDirection = Double.compare(startPos.y, endPos.y);

            double currentX = startPos.x + xDirection;
            double currentY = startPos.y + yDirection;
            while (currentX != endPos.x || currentY != endPos.y) {
                if (currentX < 0 || currentX > 7 || currentY < 0 || currentY > 7) return false; // Out of bounds
                if (board.getPiece((int) currentX, (int) currentY) != null) return false;
                currentX += xDirection;
                currentY += yDirection;
            }
            return true;
        }
        return false;
    }

    @Override
    public void update() {

    }
}