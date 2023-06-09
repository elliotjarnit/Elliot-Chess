package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.Objects.EEntity;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

public abstract class Piece extends EEntity {
    private Side side;
    private Vector2 boardPosition;

    public Piece(Side side, Vector2 boardPosition) {
        this.side = side;
        this.boardPosition = boardPosition;
    }

    public abstract boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board);

    public int getX() {
        return (int) boardPosition.x;
    }
    public void setX(int x) {
        this.boardPosition.x = x;
    }

    public int getY() {
        return (int) boardPosition.y;
    }
    public void setY(int y) {
        this.boardPosition.y = y;
    }

    public Side getSide() {
        return side;
    }

    public enum Side {
        WHITE,
        BLACK
    }
}