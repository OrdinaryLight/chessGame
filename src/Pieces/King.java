package Pieces;

import Main.Board;
import Main.Move;
import javafx.scene.image.Image;

public class King extends Piece {

    public King(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, board);
    }

    public boolean isValidMove(int newX, int newY) {
        final int dx = Math.abs(newX - x);
        final int dy = Math.abs(newY - y);
        return ((dx <= 1 && dy <= 1) || canCastle(newX, newY)) && inBounds(newX, newY);
    }

    public boolean moveCollides(int newX, int newY) {
        return false;
    }

    public Image getImage() {
        if (isWhite) {
            return new Image("Pieces/PieceImages/White_King.png");
        } else {
            return new Image("Pieces/PieceImages/Black_King.png");
        }
    }

    private boolean canCastle(int x, int y) {
        final Piece rook;

        if (this.y != y) {
            return false;
        }

        if (x == 6) {
            rook = board.getPiece(7, y);
            return rook != null && rook.isFirstMove == 0 && isFirstMove == 0 && board.getPiece(5, y) == null
                    && board.getPiece(6, y) == null
                    && !board.getCheckChecker().isKingChecked(new Move(board, rook, 5, y));
        } else if (x == 2) {
            rook = board.getPiece(0, y);
            return rook != null && rook.isFirstMove == 0 && isFirstMove == 0 && board.getPiece(3, y) == null
                    && board.getPiece(2, y) == null
                    && board.getPiece(1, y) == null
                    && !board.getCheckChecker().isKingChecked(new Move(board, rook, 3, y));
        }

        return false;
    }

}