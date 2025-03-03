package GameplayLogic.Pieces;

import javafx.scene.image.Image;

public class Rook extends Piece {

    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        return (x == newX || y == newY) && inBounds(newX, newY);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Rook.png");
        } else {
            return new Image("\\PieceImages\\Black_Rook.png");
        }
    }

}
