package GameplayLogic;

import GameplayLogic.Pieces.Piece;

public class Move {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Piece movingPiece;
    private Piece capturedPiece;

    public Move(int startX, int startY, int endX, int endY, Piece movingPiece, Piece capturedPiece) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
    }

    // Getters for the move details
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public boolean equals(Move move) {
        return (startX == move.getStartX()) && (startY == move.getStartY()) && (endX == move.getEndX())
                && (endY == move.getEndY());
    }

    public String toString() {
        String capturePart = (capturedPiece != null) ? " capturing " + capturedPiece.getClass().getSimpleName() : "";
        return movingPiece.getClass().getSimpleName() + " from (" + startX + "," + startY + ") to (" + endX + "," + endY
                + ")" + capturePart;
    }
}
