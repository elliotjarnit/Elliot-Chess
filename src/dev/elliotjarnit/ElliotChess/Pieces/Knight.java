package dev.elliotjarnit.ElliotChess.Pieces;

import dev.elliotjarnit.ElliotChess.Board;
import dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

public class Knight extends Piece {
    public Knight(Side side, Vector2 boardPosition) {
        super(side, boardPosition);
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        int dy = (int) Math.abs(endPos.y - startPos.y);
        int dx = (int) Math.abs(endPos.x - startPos.x);
        return (dy == 1 && dx == 2) || (dy == 2 && dx == 1);
    }

    @Override
    public void update() {

    }
}