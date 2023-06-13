package dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotChess.Pieces.Rook;
import dev.elliotjarnit.ElliotChess.Pieces.Knight;
import dev.elliotjarnit.ElliotChess.Pieces.Bishop;
import dev.elliotjarnit.ElliotChess.Pieces.Queen;
import dev.elliotjarnit.ElliotChess.Pieces.King;
import dev.elliotjarnit.ElliotChess.Pieces.Pawn;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Board extends EObject {
    private final Piece[][] squares;
    private final BoardSquare[] boardSquares;

    public Board() {
        squares = new Piece[8][8];
        boardSquares = new BoardSquare[64];
        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/GameBoard.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            for (EFace face : faces) {
                face.setColor(new Color(164,116,73));
            }
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

    public BoardSquare[] getBoardSquares() {
        return boardSquares;
    }

    private void initializeBoard() {
        for (int i = 0; i < boardSquares.length; i++) {
            boardSquares[i] = new BoardSquare(this.getOrigin(), new Vector2(i % 8, i / 8));
        }

        squares[0][0] = new Rook(Piece.Side.WHITE, new Vector2(0, 0));
        squares[1][0] = new Knight(Piece.Side.WHITE, new Vector2(0, 1));
        squares[2][0] = new Bishop(Piece.Side.WHITE, new Vector2(0, 2));
        squares[3][0] = new Queen(Piece.Side.WHITE, new Vector2(0, 3));
        squares[4][0] = new King(Piece.Side.WHITE, new Vector2(0, 4));
        squares[5][0] = new Bishop(Piece.Side.WHITE, new Vector2(0, 5));
        squares[6][0] = new Knight(Piece.Side.WHITE, new Vector2(0, 6));
        squares[7][0] = new Rook(Piece.Side.WHITE, new Vector2(0, 7));

        squares[0][7] = new Rook(Piece.Side.BLACK, new Vector2(7, 0));
        squares[1][7] = new Knight(Piece.Side.BLACK, new Vector2(7, 1));
        squares[2][7] = new Bishop(Piece.Side.BLACK, new Vector2(7, 2));
        squares[3][7] = new Queen(Piece.Side.BLACK, new Vector2(7, 3));
        squares[4][7] = new King(Piece.Side.BLACK, new Vector2(7, 4));
        squares[5][7] = new Bishop(Piece.Side.BLACK, new Vector2(7, 5));
        squares[6][7] = new Knight(Piece.Side.BLACK, new Vector2(7, 6));
        squares[7][7] = new Rook(Piece.Side.BLACK, new Vector2(7, 7));

//        for (int i = 0; i < 8; i++) {
//            squares[i][1] = new Pawn(Piece.Side.WHITE, new Vector2(1, i));
//            squares[i][6] = new Pawn(Piece.Side.BLACK, new Vector2(6, i));
//        }
    }

    public Piece getPiece(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
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