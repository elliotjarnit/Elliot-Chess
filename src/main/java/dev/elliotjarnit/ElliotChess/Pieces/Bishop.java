package dev.elliotjarnit.ElliotChess.Pieces;

import dev.elliotjarnit.ElliotChess.Board;
import dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Handlers.FileHandler;
import dev.elliotjarnit.elliotengine.Handlers.ObjHandler;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Utils.Vector2;

import java.io.FileNotFoundException;

public class Bishop extends Piece {
    public Bishop(Side side, Vector2 boardPosition) {
        super(side, boardPosition);

        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/bishop.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            for (EFace face : faces) {
                face.setColor(side == Side.WHITE ? EColor.WHITE : EColor.BLACK);
            }
            this.setFaces(faces);
        } catch (FileNotFoundException | ObjHandler.NotTriangleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        // DONE

        int dx = (int) Math.abs(endPos.x - startPos.x);
        int dy = (int) Math.abs(endPos.y - startPos.y);

        Piece endPiece = board.getPiece((int) endPos.x, (int) endPos.y);

        if (dy == dx) {
            int xDirection = (int) Math.signum(endPos.x - startPos.x);
            int yDirection = (int) Math.signum(endPos.y - startPos.y);

            double currentX = startPos.x + xDirection;
            double currentY = startPos.y + yDirection;
            while (currentX != endPos.x || currentY != endPos.y) {
                if (currentX < 0 || currentX > 7 || currentY < 0 || currentY > 7) return false; // Out of bounds
                if (board.getPiece((int) currentX, (int) currentY) != null) return false;
                currentX += xDirection;
                currentY += yDirection;
            }
            return endPiece == null || endPiece.getSide() != this.getSide();
        }
        return false;
    }

    @Override
    public void update() {

    }
}