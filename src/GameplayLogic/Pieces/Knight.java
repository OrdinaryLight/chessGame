package GameplayLogic.Pieces;

import java.util.ArrayList;

import GameplayLogic.Board;
import GameplayLogic.Move;
import javafx.scene.image.Image;

public class Knight extends Piece {

    public Knight(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public boolean isValidMove(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) && inBounds(newX, newY);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param knight the Piece
     * @param moves  ArrayList to add to
     * @return moves
     */
    public ArrayList<Move> getMoves(ArrayList<Move> moves, Board board) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int[][] knightMoves = { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 },
                { -1, -2 } };
        for (int[] move : knightMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            if (isCapturable(newX, newY, board)) {
                potentialMoves.add(new Move(x, y, newX, newY, this, board.getPiece(newX, newY)));
            }
        }

        return board.addLegalMoves(potentialMoves, moves);
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("\\PieceImages\\White_Knight.png");
        } else {
            return new Image("\\PieceImages\\Black_Knight.png");
        }
    }

}
