package GameplayLogic.Pieces;

import javafx.scene.image.Image;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        return (Math.abs(newX - x) == Math.abs(newY - y)) && inBounds(newX, newY);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Bishop.png");
        } else {
            return new Image("\\PieceImages\\Black_Bishop.png");
        }
    }

}
