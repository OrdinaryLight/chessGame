package GameplayLogic;

import GameplayLogic.Pieces.Piece;

//////////
/// Class is to store a specific move
/// *Used as a easier way to pass information around and to be able to store a list of moves
/// ***MovingPiece could technically be removed, but capturedPiece can't due to enpassant
/// 
public class Move {
    private int startX; // starting X of the movingPiece
    private int startY; // the starting Y of the movingPiece
    private int endX; // the ending X of the movingPiece
    private int endY; // the ending Y of the movingPiece
    private Piece movingPiece; // the Piece that will be moving
    private Piece capturedPiece; // the Piece that will be captured

    public Move(int startX, int startY, int endX, int endY, Piece movingPiece, Piece capturedPiece) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
    }

    /**
     * returns the starting X of the movingPiece
     * 
     * @return the X where the movingPiece starts
     */
    public int getStartX() {
        return startX;
    }

    /**
     * returns the starting Y of the movingPiece
     * 
     * @return the Y where the movingPiece starts
     */
    public int getStartY() {
        return startY;
    }

    /**
     * returns the ending X of the movingPiece
     * 
     * @return the X where the movingPiece will end up
     */
    public int getEndX() {
        return endX;
    }

    /**
     * returns the ending Y of the movingPiece
     * 
     * @return the Y where the movingPiece will end up
     */
    public int getEndY() {
        return endY;
    }

    /**
     * returns the movingPiece (piece that moves)
     * 
     * @return the movingPiece
     */
    public Piece getMovingPiece() {
        return movingPiece;
    }

    /**
     * returns the targetPiece (piece that will be captured)
     * 
     * @return the targetPiece
     */
    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    /**
     * equals for Move class, used for .contains mostly
     * 
     * @param move the move to check it it's equal to
     * @return whether or not the moves are equal
     */
    public boolean equals(Move move) {
        return (startX == move.getStartX()) && (startY == move.getStartY()) && (endX == move.getEndX())
                && (endY == move.getEndY());
    }

    /**
     * toString for Move class, used for testing and debugging
     * 
     * @return
     */
    public String toString() {
        String capturePart = (capturedPiece != null) ? " capturing " + capturedPiece.getClass().getSimpleName() : "";
        return movingPiece.getClass().getSimpleName() + " from (" + startX + "," + startY + ") to (" + endX + "," + endY
                + ")" + capturePart;
    }
}
