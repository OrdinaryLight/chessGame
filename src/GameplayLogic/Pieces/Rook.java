package GameplayLogic.Pieces;

public class Rook extends Piece {

    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        return (x == newX || y == newY) && inBounds(newX, newY);
    }

}
