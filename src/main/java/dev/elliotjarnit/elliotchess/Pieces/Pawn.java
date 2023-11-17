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

public class Pawn extends Piece {
    public Pawn(Side side, Vector2 boardPosition) {
        super(side, boardPosition);

        try {
            String[] data = FileHandler.loadFileFromResources("Pawn.obj");
            EFace[] faces = ObjHandler.loadData(data);
            for (EFace face : faces) {
                face.setColor(side == Side.WHITE ? EColor.WHITE : EColor.BLACK);
            }
            this.setFaces(faces);
        } catch (NotTriangleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        // DONE

        int dx;
        if (this.getSide() == Side.WHITE) {
            dx = (int) (endPos.x - startPos.x);
        } else {
            dx = (int) (startPos.x - endPos.x);
        }
        int dy = (int) Math.abs(endPos.y - startPos.y);

        boolean canDoubleMove = false;

        if (this.getSide() == Side.WHITE) {
            if (startPos.x == 1) {
                canDoubleMove = true;
            }
        } else {
            if (startPos.x == 6) {
                canDoubleMove = true;
            }
        }

        Piece endPiece = board.getPiece((int) endPos.x, (int) endPos.y);

        if (dy == 0 && dx == 1 && endPiece == null) {
            return true;
        } else if (dy == 0 && dx == 2 && canDoubleMove && endPiece == null) {
            return true;
        } else if (dy == 1 && dx == 1 && endPiece != null && endPiece.getSide() != this.getSide()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update() {

    }
}