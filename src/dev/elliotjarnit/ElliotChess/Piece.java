package dev.elliotjarnit.ElliotChess;

public abstract class Piece {
    private boolean isWhite;
    private int x;
    private int y;

    public Piece(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    public boolean isWhite() {return isWhite;}

    public int getX() {return x;}

    public int getY() {return y;}

    public void setX(int x) {this.x = x;}

    public void setY(int y) {this.y = y;}

    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Board brd);
}