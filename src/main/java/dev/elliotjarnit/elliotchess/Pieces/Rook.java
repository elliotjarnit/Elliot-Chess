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

public class Rook extends Piece
{
    public Rook(Side side, Vector2 boardPosition) {
        super(side, boardPosition);

        try {
            String[] data = FileHandler.loadFileFromResources("Rook.obj");
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

        Piece endPiece = board.getPiece((int) endPos.x, (int) endPos.y);

        if (startPos.x == endPos.x || startPos.y == endPos.y) {
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
            return endPiece == null || endPiece.getSide() != this.getSide();
        }
        return false;
    }

    @Override
    public void update() {

    }
}