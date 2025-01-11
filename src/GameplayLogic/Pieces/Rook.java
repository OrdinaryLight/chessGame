package GameplayLogic.Pieces;

import java.util.ArrayList;

import Constants.BoardConstants;
import GameplayLogic.Board;
import GameplayLogic.Move;
import javafx.scene.image.Image;

public class Rook extends Piece {

    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        return (x == newX || y == newY) && inBounds(newX, newY);
    }

    public ArrayList<Move> getMoves(ArrayList<Move> moves, Board board) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        boolean upBlocked = false;
        boolean downBlocked = false;
        boolean leftBlocked = false;
        boolean rightBlocked = false;

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

        }

        return board.addLegalMoves(potentialMoves, moves);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Rook.png");
        } else {
            return new Image("\\PieceImages\\Black_Rook.png");
        }
    }

}
