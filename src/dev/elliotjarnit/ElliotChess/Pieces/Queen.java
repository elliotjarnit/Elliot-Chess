package dev.elliotjarnit.ElliotChess.Pieces;

import dev.elliotjarnit.ElliotChess.Board;
import dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

public class Queen extends Piece
{
    public Queen(Side side, Vector2 boardPosition) {
        super(side, boardPosition);
    }

    @Override
    public boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board) {
        int dx = (int) Math.abs(endPos.x - startPos.x);
        int dy = (int) Math.abs(endPos.y - startPos.y);
        if (startPos.x == endPos.x || startPos.y == endPos.y || dx == dy) {
            int xDirection = Integer.compare((int) endPos.x, (int) startPos.x);
            int yDirection = Integer.compare((int) endPos.y, (int) startPos.y);

            int currentX = (int) (startPos.x + xDirection);
            int currentY = (int) (startPos.y + yDirection);
            while (currentX != endPos.x || currentY != endPos.y) {
                if (board.getPiece(currentX, currentY) != null) {
                    return false;
                }
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