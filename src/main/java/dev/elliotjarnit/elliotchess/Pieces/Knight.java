package dev.elliotjarnit.elliotchess.Pieces;

import dev.elliotjarnit.elliotchess.Board;
import dev.elliotjarnit.elliotchess.Piece;
import dev.elliotjarnit.elliotengine.Exceptions.NotTriangleException;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Handlers.FileHandler;
import dev.elliotjarnit.elliotengine.Handlers.ObjHandler;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Utils.Vector2;

import java.io.FileNotFoundException;

public class Knight extends Piece {
    public Knight(Side side, Vector2 boardPosition) {
        super(side, boardPosition);

        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/knight.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            for (EFace face : faces) {
                face.setColor(side == Side.WHITE ? EColor.WHITE : EColor.BLACK);
            }
            this.setFaces(faces);
        } catch (FileNotFoundException | NotTriangleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        // DONE
        
        int dy = (int) Math.abs(endPos.y - startPos.y);
        int dx = (int) Math.abs(endPos.x - startPos.x);

        Piece endPiece = board.getPiece((int) endPos.x, (int) endPos.y);

        if (dy == 1 && dx == 2 && endPiece == null) {
            return true;
        } else if (dy == 2 && dx == 1 && endPiece == null) {
            return true;
        } else if (dy == 1 && dx == 2 && endPiece != null && endPiece.getSide() != this.getSide()) {
            return true;
        } else if (dy == 2 && dx == 1 && endPiece != null && endPiece.getSide() != this.getSide()) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {

    }
}