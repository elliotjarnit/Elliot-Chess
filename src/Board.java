public class Board
{
    private Piece[][] squares;

    public Board()
    {
        squares = new Piece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        squares[0][0] = new Rook(true, 0, 0);
        squares[0][1] = new Knight(true, 0, 1);
        squares[0][2] = new Bishop(true, 0, 2);
        squares[0][3] = new Queen(true, 0, 3);
        squares[0][4] = new King(true, 0, 4);
        squares[0][5] = new Bishop(true, 0, 5);
        squares[0][6] = new Knight(true, 0, 6);
        squares[0][7] = new Rook(true, 0, 7);

        squares[7][0] = new Rook(false, 7, 0);
        squares[7][1] = new Knight(false, 7, 1);
        squares[7][2] = new Bishop(false, 7, 2);
        squares[7][3] = new Queen(false, 7, 3);
        squares[7][4] = new King(false, 7, 4);
        squares[7][5] = new Bishop(false, 7, 5);
        squares[7][6] = new Knight(false, 7, 6);
        squares[7][7] = new Rook(false, 7, 7);

        for (int i = 0; i < 8; i++) {
            squares[1][i] = new Pawn(true, 1, i);
            squares[6][i] = new Pawn(false, 6, i);
        }
    }

    public Piece getPiece(int x, int y) {
        return squares[x][y];
    }

    public void movePiece(int startX, int startY, int endX, int endY) {
        Piece piece = squares[startX][startY];
        Piece capturedPiece = null;
        if (piece != null && piece.isValidMove(startX, startY, endX, endY, this))
        {
            squares[startX][startY] = null;
            capturedPiece = squares[endX][endY];
            squares[endX][endY] = piece;
            piece.setX(endX);
            piece.setY(endY);
        } else {
            System.out.println("Invalid");
        }

        if (capturedPiece instanceof King)
        {
            endGame(piece.isWhite());
        }
    }

    private void endGame(boolean whiteWins) {
        String winnerColor = whiteWins ? "white" : "black";
        System.out.println(winnerColor + " wins the game!");
    }
}