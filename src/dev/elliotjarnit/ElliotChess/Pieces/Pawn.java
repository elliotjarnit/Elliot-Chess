package dev.elliotjarnit.ElliotChess.Pieces;

import dev.elliotjarnit.ElliotChess.Board;
import dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

public class Pawn extends Piece {
    public Pawn(Side side, Vector2 boardPosition) {
        super(side, boardPosition);
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        int dy = (int) (endPos.y - startPos.y);
        int dx = (int) Math.abs(endPos.x - startPos.x);
        if (this.getSide() == Side.WHITE) {
            if (dy == 1 && dx == 0 && board.getPiece((int) endPos.x, (int) endPos.y) == null) {
                return true;
            } else if (dy == 1 && dx == 1 && board.getPiece((int) endPos.x, (int) endPos.y) != null) {
                return true;
            } else return dy == 2 && dx == 0 && startPos.y == 1;
        } else {
            if (dy == -1 && dx == 0 && board.getPiece((int) endPos.x, (int) endPos.y) == null) {
                return true;
            }
            else if (dy == -1 && dx == 1 && board.getPiece((int) endPos.x, (int) endPos.y) != null) {
                return true;
            } else return dy == -2 && dx == 0 && startPos.y == 6 && board.getPiece((int) endPos.x, (int) endPos.y) == null;
        }
    }

    @Override
    public void update() {

    }
}