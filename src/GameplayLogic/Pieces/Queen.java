package GameplayLogic.Pieces;

import javafx.scene.image.Image;

public class Queen extends Piece {

    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return (dx == dy || dx == 0 || dy == 0) && inBounds(newX, newY);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Queen.png");
        } else {
            return new Image("\\PieceImages\\Black_Queen.png");
        }
    }

}
