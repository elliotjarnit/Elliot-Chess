package dev.elliotjarnit.ElliotChess.Pieces;

import dev.elliotjarnit.ElliotChess.Board;
import dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

import java.io.FileNotFoundException;

public class Pawn extends Piece {
    public Pawn(Side side, Vector2 boardPosition) {
        super(side, boardPosition);

        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/pawn.obj";
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
        int dx = (int) (endPos.x - startPos.x);
        int dy = (int) Math.abs(endPos.y - startPos.y);
        if (this.getSide() == Side.WHITE) {
            if (dy == 1 && dx == 0 && board.getPiece((int) endPos.x, (int) endPos.y) == null) return true;
            else if (dy == 1 && dx == 1 && board.getPiece((int) endPos.x, (int) endPos.y) != null) return true;
            else return dx == 2 && dy == 0 && startPos.y == 1;
        } else {
            if (dx == -1 && dy == 0 && board.getPiece((int) endPos.x, (int) endPos.y) == null) return true;
            else if (dx == -1 && dy == 1 && board.getPiece((int) endPos.x, (int) endPos.y) != null) return true;
            else return dx == -2 && dy == 0 && startPos.y == 6 && board.getPiece((int) endPos.x, (int) endPos.y) == null;
        }
    }

    @Override
    public void update() {

    }
}