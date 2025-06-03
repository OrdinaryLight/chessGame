package Pieces;

import Main.Board;
import javafx.scene.image.Image;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, board);
    }

    public boolean isValidMove(int newX, int newY) {
        return (Math.abs(newX - x) == Math.abs(newY - y)) && inBounds(newX, newY);
    }

    @Override
    public boolean moveCollides(int newX, int newY) {
        final int x = getX();
        final int y = getY();
        final int dx = newX - x;
        final int dirX = newX > x ? 1 : -1;
        final int dirY = newY > y ? 1 : -1;

        for (int i = 1; i < Math.abs(dx); i++) {
            if (board.getPiece(x + i * dirX, y + i * dirY) != null) {
                return true;
            }
        }

        return false;
    }

    public Image getImage() {
        if (isWhite) {
            return new Image(("Pieces/PieceImages/White_Bishop.png"));
        } else {
            return new Image(("Pieces/PieceImages/Black_Bishop.png"));
        }
    }

}