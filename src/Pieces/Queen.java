package Pieces;

import Main.Board;
import javafx.scene.image.Image;

public class Queen extends Piece {
    public Queen(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, board);
    }

    public boolean isValidMove(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return (dx == dy || dx == 0 || dy == 0) && inBounds(newX, newY);
    }

    @Override
    public boolean moveCollides(int newX, int newY) {
        final int x = getX();
        final int y = getY();
        final int dx = Math.abs(newX - x);
        final int dy = Math.abs(newY - y);
        final int dirX = newX > x ? 1 : -1;
        final int dirY = newY > y ? 1 : -1;

        if (dy == 0 || dx == 0) {
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
        } else {
            for (int i = 1; i < dx; i++) {
                if (board.getPiece(x + i * dirX, y + i * dirY) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("Pieces/PieceImages/White_Queen.png");
        } else {
            return new Image("Pieces/PieceImages/Black_Queen.png");
        }
    }

}