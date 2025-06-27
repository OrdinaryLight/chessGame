package Main;

import Pieces.Piece;

public class Move {
    int x;
    int y;
    int newX;
    int newY;

    Piece piece;
    Piece capturedPiece;

    public Move(Board board, Piece piece, int newX, int newY) {
        this.x = piece.getX();
        this.y = piece.getY();
        this.newX = newX;
        this.newY = newY;

        this.piece = piece;
        this.capturedPiece = board.getPiece(newX, newY);
    }
}
