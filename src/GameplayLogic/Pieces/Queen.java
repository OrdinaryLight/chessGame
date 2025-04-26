package GameplayLogic.Pieces;

import java.util.ArrayList;

import Constants.BoardConstants;
import GameplayLogic.Board;
import GameplayLogic.Move;
import javafx.scene.image.Image;

public class Queen extends Piece {

    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return (dx == dy || dx == 0 || dy == 0) && inBounds(newX, newY);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param queen the Piece
     * @param moves ArrayList to add to
     * @return moves
     */
    public ArrayList<Move> getMoves(ArrayList<Move> moves, Board board) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        boolean upBlocked = false;
        boolean upRightBlocked = false;
        boolean downBlocked = false;
        boolean downRightBlocked = false;
        boolean leftBlocked = false;
        boolean upLeftBlocked = false;
        boolean rightBlocked = false;
        boolean downLeftBlocked = false;

        for (int i = 1; i < BoardConstants.SIZE; i++) {
            if (isCapturable(x + i, y, board) && !rightBlocked) {
                if (board.getPiece(x + i, y) != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y, this, board.getPiece(x + i, y)));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(x - i, y, board) && !leftBlocked) {
                if (board.getPiece(x - i, y) != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y, this, board.getPiece(x - i, y)));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(x, y + i, board) && !upBlocked) {
                if (board.getPiece(x, y + i) != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y + i, this, board.getPiece(x, y + i)));
            } else {
                upBlocked = true;
            }

            if (isCapturable(x, y - i, board) && !downBlocked) {
                if (board.getPiece(x, y - i) != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y - i, this, board.getPiece(x, y - i)));
            } else {
                downBlocked = true;
            }

            if (isCapturable(x + i, y + i, board) && !upRightBlocked) {
                if (board.getPiece(x + i, y + i) != null) {
                    upRightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y + i, this, board.getPiece(x + i, y + i)));
            } else {
                upRightBlocked = true;
            }

            if (isCapturable(x - i, y - i, board) && !downLeftBlocked) {
                if (board.getPiece(x - i, y - i) != null) {
                    downLeftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y - i, this, board.getPiece(x - i, y - i)));
            } else {
                downLeftBlocked = true;
            }

            if (isCapturable(x - i, y + i, board) && !upLeftBlocked) {
                if (board.getPiece(x - i, y + i) != null) {
                    upLeftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y + i, this, board.getPiece(x - i, y + i)));
            } else {
                upLeftBlocked = true;
            }

            if (isCapturable(x + i, y - i, board) && !downRightBlocked) {
                if (board.getPiece(x + i, y - i) != null) {
                    downRightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y - i, this, board.getPiece(x + i, y - i)));
            } else {
                downRightBlocked = true;
            }

        }
        return addLegalMoves(potentialMoves, moves, board);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Queen.png");
        } else {
            return new Image("\\PieceImages\\Black_Queen.png");
        }
    }

}
