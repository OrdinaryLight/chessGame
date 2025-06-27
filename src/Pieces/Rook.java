package Pieces;

import Main.Board;
import javafx.scene.image.Image;

public class Rook extends Piece {

    public Rook(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, board);
    }

    public boolean isValidMove(int newX, int newY) {
        return (x == newX || y == newY) && inBounds(newX, newY);
    }

    @Override
    public boolean moveCollides(final int newX, final int newY) {
        final int x = getX();
        final int y = getY();
        final int dx = Math.abs(newX - x);
        final int dy = Math.abs(newY - y);
        final int dirX = newX > x ? 1 : -1;
        final int dirY = newY > y ? 1 : -1;

        for (int i = 1; i < dy; i++) {
            if (board.getPiece(x, y + dirY * i) != null) {
                return true;
            }
        }
        for (int i = 1; i < dx; i++) {
            if (board.getPiece(x + dirX * i, y) != null) {
                return true;
            }
        }
        return false;
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("Pieces/PieceImages/White_Rook.png");
        } else {
            return new Image("Pieces/PieceImages/Black_Rook.png");
        }
    }

    @Override
    public String toString() {
        return "Rook []";
    }

}