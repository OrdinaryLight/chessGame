package GameplayLogic.Pieces;

import Constants.BoardConstants;

public abstract class Piece {
    protected int x;
    protected int y;
    protected boolean isWhite;

    public Piece(int x, int y, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
    }

    // must be inbounds aswell as a space that could be moved to by that piece
    public abstract boolean isValidMove(int newX, int newY);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean inBounds(int x, int y) {
        return (x >= 0 && x < BoardConstants.SIZE) && (y >= 0 && y < BoardConstants.SIZE);
    }
}
