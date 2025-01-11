package GameplayLogic.Pieces;

import java.util.ArrayList;

import Constants.BoardConstants;
import GameplayLogic.Board;
import GameplayLogic.Move;
import javafx.scene.image.Image;

public class Pawn extends Piece {

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
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

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param pawn  the Piece
     * @param moves ArrayList to add to
     * @return moves
     */
    public ArrayList<Move> getMoves(ArrayList<Move> moves, Board board) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int direction = isWhite ? 1 : -1;

        // Forward move
        if (inBounds(x, y + direction) && board.getPiece(x, y + direction) == null) {
            potentialMoves.add(new Move(x, y, x, y + direction, this, null));
            // Double move from starting position
            if ((!isWhite && y == BoardConstants.SIZE - 2) || (isWhite && y == 1)) {
                if (inBounds(x, y + 2 * direction) && board.getPiece(x, y + 2 * direction) == null) {
                    potentialMoves.add(new Move(x, y, x, y + 2 * direction, this, null));
                }
            }
        }

        if (inBounds(x + 1, y + direction) && board.getPiece(x + 1, y + direction) != null
                && board.getPiece(x + 1, y + direction).isWhite() != isWhite) {
            potentialMoves.add(new Move(x, y, x + 1, y + direction, this, board.getPiece(x + 1, y + direction)));
        }
        if (inBounds(x - 1, y + direction) && board.getPiece(x - 1, y + direction) != null
                && board.getPiece(x - 1, y + direction).isWhite() != isWhite) {
            potentialMoves.add(new Move(x, y, x - 1, y + direction, this, board.getPiece(x - 1, y + direction)));
        }

        // En passant
        if (inBounds(x + 1, y + direction) && isEnPassantPossible(x + 1, y, direction, board)) {
            potentialMoves.add(new Move(x, y, x + 1, y + direction, this, board.getPiece(x + 1,
                    y)));
        }
        if (inBounds(x - 1, y + direction) && isEnPassantPossible(x - 1, y, direction, board)) {
            potentialMoves.add(new Move(x, y, x - 1, y + direction, this, board.getPiece(x - 1,
                    y)));
        }

        return board.addLegalMoves(potentialMoves, moves);
    }

    /**
     * checks if enpassant is valid here
     * 
     * @param piece     starting Piece
     * @param targetX   X of pawn
     * @param targetY   Y of pawn
     * @param direction direction your moving
     * @return whether or not enpassant is possible
     */
    private boolean isEnPassantPossible(int targetX, int targetY, int direction, Board board) {
        Piece adjacentPiece = board.getPiece(targetX, targetY);

        if (isWhite && board.getPassant(isWhite) == adjacentPiece && board.getPassant(isWhite) != null) {
            return isCapturable(targetX, targetY + direction, board);
        } else if (isWhite && board.getPassant(!isWhite) == adjacentPiece && board.getPassant(!isWhite) != null) {
            return isCapturable(targetX, targetY + direction, board);
        }

        return false;
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Pawn.png");
        } else {
            return new Image("\\PieceImages\\Black_Pawn.png");
        }
    }
}
