package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.Objects.EEntity;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

import java.io.FileNotFoundException;

public abstract class Piece extends EEntity {
    private final Side side;
    private final Vector2 boardPosition;

    public Piece(Side side, Vector2 boardPosition) {
        super(new Vector3(0, 0, 0));
        this.side = side;
        this.boardPosition = boardPosition;

        this.calculateWorldPos();
    }

    public void calculateWorldPos() {
        Vector2 NormalizedBoardPosition = new Vector2(boardPosition.x / 7, boardPosition.y / 7);

        NormalizedBoardPosition.x = (NormalizedBoardPosition.x * 2) - 1;
        NormalizedBoardPosition.y = (NormalizedBoardPosition.y * 2) - 1;

        NormalizedBoardPosition.x *= 60;
        NormalizedBoardPosition.y *= 60;

        this.setOrigin(new Vector3(NormalizedBoardPosition.x, 20, NormalizedBoardPosition.y));
    }

    public abstract boolean isValidMove(Vector2 startPos, Vector2 endPos, Board board);

    public int getX() {
        return (int) boardPosition.x;
    }
    public void setX(int x) {
        this.boardPosition.x = x;
        this.calculateWorldPos();
    }

    public int getY() {
        return (int) boardPosition.y;
    }
    public void setY(int y) {
        this.boardPosition.y = y;
        this.calculateWorldPos();
    }

    public Side getSide() {
        return side;
    }

    public enum Side {
        WHITE,
        BLACK
    }
}