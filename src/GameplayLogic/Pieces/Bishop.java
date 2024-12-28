package GameplayLogic.Pieces;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        return (Math.abs(newX - x) == Math.abs(newY - y)) && inBounds(newX, newY);
    }

}
