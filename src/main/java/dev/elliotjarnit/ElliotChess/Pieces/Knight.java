package main.java.dev.elliotjarnit.ElliotChess.Pieces;

import main.java.dev.elliotjarnit.ElliotChess.Board;
import main.java.dev.elliotjarnit.ElliotChess.Piece;
import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

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
        } catch (FileNotFoundException | ObjHandler.NotTriangleException e) {
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