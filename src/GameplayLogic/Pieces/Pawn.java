package GameplayLogic.Pieces;

import Constants.BoardConstants;

public class Pawn extends Piece {
    boolean isPassentable;

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        isPassentable = false;
    }

    public boolean isValidMove(int newX, int newY) {
        int direction;
        int dx = Math.abs(x - newX);
        int sdy = newY - y;
        int doubleMoveSquare;

        if (!inBounds(newX, newY) || dx > 1) {
            return false;
        }

        if (isWhite) {
            direction = 1;
            doubleMoveSquare = 1;
        } else {
            direction = -1;
            doubleMoveSquare = BoardConstants.SIZE - 2;
        }

        // one square moved
        if (sdy == direction) {
            return true;
        }
        // two squares moved.
        if (dx == 0 && y + 2 * direction == newY && y == doubleMoveSquare) {
            return true;
        }
        // dx == 1

        return false;
    }

    public boolean isPassentable(Piece firstPiece) {
        return isPassentable && isWhite != firstPiece.isWhite;
    }

    public void setPassentable(boolean passentable) {
        isPassentable = passentable;
    }
}
