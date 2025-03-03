package GameplayLogic.Pieces;

import javafx.scene.image.Image;

public class King extends Piece {

    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return (dx <= 1 && dy <= 1) && inBounds(newX, newY);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_King.png");
        } else {
            return new Image("\\PieceImages\\Black_King.png");
        }
    }

}
