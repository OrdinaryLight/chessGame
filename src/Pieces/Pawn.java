package Pieces;

import Main.Board;
import javafx.scene.image.Image;

public class Pawn extends Piece {

    public Pawn(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, board);
    }

    public boolean isValidMove(int newX, int newY) {
        final int dir = isWhite ? -1 : 1;

        if (!inBounds(newX, newY)) {
            return false;
        }

        if ((y + dir == newY) && (x == newX) && board.getPiece(newX, newY) == null) {
            return true;
        }

        if ((y + 2 * dir == newY) && (x == newX) && isFirstMove && board.getPiece(newX, newY) == null
                && board.getPiece(newX, newY - dir) == null) {
            return true;
        }

        if ((y + dir == newY) && (x == newX - 1) && (board.getPiece(newX, newY) != null)) {
            return true;
        }

        if ((y + dir == newY) && (x == newX + 1) && (board.getPiece(newX, newY) != null)) {
            return true;
        }

        if (board.getEnPassantSquare() == board.getTileNum(newX, newY) && Math.abs(newX - x) == 1 && (newY - y == dir)
                && board.getPiece(newX, newY - dir) != null) {
            return true;
        }

        return false;

    }

    public boolean moveCollides(int newX, int newY) {
        return false;
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("Pieces/PieceImages/White_Pawn.png");
        } else {
            return new Image("Pieces/PieceImages/Black_Pawn.png");
        }
    }
}