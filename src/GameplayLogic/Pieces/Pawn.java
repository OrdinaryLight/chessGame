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
        int doubleMoveSquare;

        if (!inBounds(newX, newY)) {
            return false;
        }

        if (dx > 1) {
            return false;
        }

        if (isWhite) {
            direction = -1;
        } else {
            direction = 1;
        }

        // potential capture
        if (dx == 1 && y + direction == newY) {
            return true;
        }

        // one square moved
        if (y + direction == newY) {
            return true;
        }

        if (isWhite) {
            doubleMoveSquare = 1;
        } else {
            doubleMoveSquare = BoardConstants.SIZE - 2;
        }

        // two squares moved.
        if ((y + 2 * direction == newY && y == doubleMoveSquare)) {
            return true;
        }

        return false;
    }

    public boolean isPassentable(Piece firstPiece) {
        return isPassentable && isWhite != firstPiece.isWhite;
    }

    public void setPassentable(boolean passentable) {
        isPassentable = passentable;
    }
}
