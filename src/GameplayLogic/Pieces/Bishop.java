package GameplayLogic.Pieces;

import java.util.ArrayList;

import Constants.BoardConstants;
import GameplayLogic.Board;
import GameplayLogic.Move;
import javafx.scene.image.Image;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        return (Math.abs(newX - x) == Math.abs(newY - y)) && inBounds(newX, newY);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param bishop the Piece
     * @param moves  ArrayList to add to
     * @return moves
     */
    public ArrayList<Move> getMoves(ArrayList<Move> moves, Board board) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        boolean upBlocked = false;
        boolean downBlocked = false;
        boolean leftBlocked = false;
        boolean rightBlocked = false;

        for (int i = 1; i < BoardConstants.SIZE; i++) {
            if (isCapturable(x + i, y + i, board) && !rightBlocked) {
                if (board.getPiece(x + i, y + i) != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y + i, this, board.getPiece(x + i, y + i)));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(x - i, y - i, board) && !leftBlocked) {
                if (board.getPiece(x - i, y - i) != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y - i, this, board.getPiece(x - i, y - i)));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(x - i, y + i, board) && !upBlocked) {
                if (board.getPiece(x - i, y + i) != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y + i, this, board.getPiece(x - i, y + i)));
            } else {
                upBlocked = true;
            }

            if (isCapturable(x + i, y - i, board) && !downBlocked) {
                if (board.getPiece(x + i, y - i) != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y - i, this, board.getPiece(x + i, y - i)));
            } else {
                downBlocked = true;
            }

        }
        return board.addLegalMoves(potentialMoves, moves);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Bishop.png");
        } else {
            return new Image("\\PieceImages\\Black_Bishop.png");
        }
    }

}
