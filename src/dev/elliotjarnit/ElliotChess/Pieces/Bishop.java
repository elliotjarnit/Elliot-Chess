package dev.elliotjarnit.ElliotChess.Pieces;

import dev.elliotjarnit.ElliotChess.Board;
import dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

public class Bishop extends Piece {
    public Bishop(Side side, Vector2 boardPosition) {
        super(side, boardPosition);
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        int dx = (int) Math.abs(endPos.x - startPos.x);
        int dy = (int) Math.abs(endPos.y - startPos.y);
        if (dy == dx) {
            int xDirection = Double.compare(endPos.y, startPos.y);
            int yDirection = Double.compare(endPos.x, startPos.y);

            double currentX = startPos.y + xDirection;
            double currentY = startPos.x + yDirection;
            while (currentX != endPos.y || currentY != endPos.x)
            {
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