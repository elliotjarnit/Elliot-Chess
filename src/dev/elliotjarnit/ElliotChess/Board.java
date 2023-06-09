package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotChess.Pieces.Rook;
import dev.elliotjarnit.ElliotChess.Pieces.Knight;
import dev.elliotjarnit.ElliotChess.Pieces.Bishop;
import dev.elliotjarnit.ElliotChess.Pieces.Queen;
import dev.elliotjarnit.ElliotChess.Pieces.King;
import dev.elliotjarnit.ElliotChess.Pieces.Pawn;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Board extends EObject {
    private final Piece[][] squares;

    public Board() {
        squares = new Piece[8][8];
        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/GameBoard.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            System.out.println(Arrays.toString(faces));
            this.setFaces(faces);
        } catch (FileNotFoundException | ObjHandler.NotTriangleException e) {
            e.printStackTrace();
        }
        initializeBoard();
    }

    @Override
    public void update() {

    }

    public Piece[] getPieces() {
        int count = 0;
        for (Piece[] row : squares) {
            for (Piece piece : row) {
                if (piece != null) {
                    count++;
                }
            }
        }
        Piece[] pieces = new Piece[count];
        int index = 0;
        for (Piece[] row : squares) {
            for (Piece piece : row) {
                if (piece != null) {
                    pieces[index] = piece;
                    index++;
                }
            }
        }
        return pieces;
    }

    private void initializeBoard() {
        squares[0][0] = new Rook(Piece.Side.WHITE, new Vector2(0, 0));
        squares[0][1] = new Knight(Piece.Side.WHITE, new Vector2(0, 1));
        squares[0][2] = new Bishop(Piece.Side.WHITE, new Vector2(0, 2));
        squares[0][3] = new Queen(Piece.Side.WHITE, new Vector2(0, 3));
        squares[0][4] = new King(Piece.Side.WHITE, new Vector2(0, 4));
        squares[0][5] = new Bishop(Piece.Side.WHITE, new Vector2(0, 5));
        squares[0][6] = new Knight(Piece.Side.WHITE, new Vector2(0, 6));
        squares[0][7] = new Rook(Piece.Side.WHITE, new Vector2(0, 7));

        squares[7][0] = new Rook(Piece.Side.BLACK, new Vector2(7, 0));
        squares[7][1] = new Knight(Piece.Side.BLACK, new Vector2(7, 1));
        squares[7][2] = new Bishop(Piece.Side.BLACK, new Vector2(7, 2));
        squares[7][3] = new Queen(Piece.Side.BLACK, new Vector2(7, 3));
        squares[7][4] = new King(Piece.Side.BLACK, new Vector2(7, 4));
        squares[7][5] = new Bishop(Piece.Side.BLACK, new Vector2(7, 5));
        squares[7][6] = new Knight(Piece.Side.BLACK, new Vector2(7, 6));
        squares[7][7] = new Rook(Piece.Side.BLACK, new Vector2(7, 7));

        for (int i = 0; i < 8; i++) {
            squares[1][i] = new Pawn(Piece.Side.WHITE, new Vector2(1, i));
            squares[6][i] = new Pawn(Piece.Side.BLACK, new Vector2(6, i));
        }
    }

    public Piece getPiece(int x, int y) {
        return squares[y][x];
    }

    public void movePiece(Vector2 startPos, Vector2 endPos) throws InvalidMoveException {
        Piece piece = squares[(int) startPos.y][(int) startPos.x];
        Piece capturedPiece = null;
        if (piece != null && piece.isValidMove(startPos, endPos, this)) {
            squares[(int) startPos.y][(int) startPos.x] = null;
            capturedPiece = squares[(int) endPos.y][(int) endPos.x];
            squares[(int) endPos.y][(int) endPos.x] = piece;
            piece.setX((int) endPos.x);
            piece.setY((int) endPos.y);
        } else {
            throw new InvalidMoveException("Invalid move");
        }

        if (capturedPiece instanceof King) {
            endGame(capturedPiece.getSide());
        }
    }

    public static class InvalidMoveException extends Exception {
        public InvalidMoveException(String errorMessage) {
            super(errorMessage);
        }
    }

    private void endGame(Piece.Side winner) {
        // TODO: Implement end game
    }
}