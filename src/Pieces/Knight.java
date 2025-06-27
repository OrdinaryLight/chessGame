package Pieces;

import Main.Board;
import javafx.scene.image.Image;

public class Knight extends Piece {

    public Knight(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, board);
    }

    public boolean isValidMove(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) && inBounds(newX, newY);
    }

    public boolean moveCollides(int newX, int newY) {
        return false;
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("Pieces/PieceImages/White_Knight.png");
        } else {
            return new Image("Pieces/PieceImages/Black_Knight.png");
        }
    }

}
