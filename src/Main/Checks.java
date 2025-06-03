package Main;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;

public class Checks {
    Board board;

    public Checks(Board board) {
        this.board = board;
    }

    public boolean isKingChecked(Move move) {
        final Piece king = board.getKing(move.piece.isWhite());
        final int kingX;
        final int kingY;
        if (move.piece instanceof King) {
            kingX = move.newX;
            kingY = move.newY;
        } else {
            kingX = king.getX();
            kingY = king.getY();
        }

        return hitByRook(move.newX, move.newY, king, kingX, kingY, 0, 1, move) ||
                hitByRook(move.newX, move.newY, king, kingX, kingY, 1, 0, move) ||
                hitByRook(move.newX, move.newY, king, kingX, kingY, 0, -1, move) ||
                hitByRook(move.newX, move.newY, king, kingX, kingY, -1, 0, move) ||
                hitByBishop(move.newX, move.newY, king, kingX, kingY, 1, 1, move) ||
                hitByBishop(move.newX, move.newY, king, kingX, kingY, 1, -1, move) ||
                hitByBishop(move.newX, move.newY, king, kingX, kingY, -1, 1, move) ||
                hitByBishop(move.newX, move.newY, king, kingX, kingY, -1, -1, move) ||
                hitByKing(king, kingX, kingY) ||
                hitByPawn(move.newX, move.newY, king, kingX, kingY, move) ||
                hitbyKnight(move.newX, move.newY, king, kingX, kingY, move);
    }

    private boolean hitByRook(int x, int y, Piece king, int kingX, int kingY, int checkX, int checkY, Move move) {
        for (int i = 1; i < Constants.SIZE; i++) {
            if (kingX + (i * checkX) == x && kingY + (i * checkY) == y) {
                break;
            }

            final Piece piece = board.getPiece(kingX + (i * checkX), kingY + (i * checkY));
            if (piece != null && piece != move.piece) {
                if (piece.isWhite() != king.isWhite() && (piece instanceof Rook || piece instanceof Queen)) {
                    return true;
                }
                break;
            }
        }

        return false;
    }

    private boolean hitByBishop(int x, int y, Piece king, int kingX, int kingY, int checkX, int checkY, Move move) {
        for (int i = 1; i < Constants.SIZE; i++) {
            if (kingX - (i * checkX) == x && kingY - (i * checkY) == y) {
                break;
            }

            final Piece piece = board.getPiece(kingX - (i * checkX), kingY - (i * checkY));
            if (piece != null && piece != move.piece) {
                if (piece.isWhite() != king.isWhite() && (piece instanceof Bishop || piece instanceof Queen)) {
                    return true;
                }
                break;
            }
        }

        return false;
    }

    private boolean hitbyKnight(int x, int y, Piece king, int kingX, int kingY, Move move) {
        return (doesKnightCheck(board.getPiece(kingX - 1, kingY - 2), king, kingX, kingY) ||
                doesKnightCheck(board.getPiece(kingX - 1, kingY + 2), king, kingX, kingY) ||
                doesKnightCheck(board.getPiece(kingX + 1, kingY - 2), king, kingX, kingY) ||
                doesKnightCheck(board.getPiece(kingX + 1, kingY + 2), king, kingX, kingY) ||
                doesKnightCheck(board.getPiece(kingX - 2, kingY - 1), king, kingX, kingY) ||
                doesKnightCheck(board.getPiece(kingX - 2, kingY + 1), king, kingX, kingY) ||
                doesKnightCheck(board.getPiece(kingX + 2, kingY - 1), king, kingX, kingY) ||
                doesKnightCheck(board.getPiece(kingX + 2, kingY + 1), king, kingX, kingY));
    }

    private boolean doesKnightCheck(Piece piece, Piece king, int x, int y) {
        return piece != null && piece.isWhite() != king.isWhite() && piece instanceof Knight
                && !(piece.getX() == x && piece.getY() == y);
    }

    private boolean hitByKing(Piece king, int kingX, int kingY) {
        return (doesKingCheck(board.getPiece(kingX - 1, kingY), king, kingX, kingY) ||
                doesKingCheck(board.getPiece(kingX + 1, kingY), king, kingX, kingY) ||
                doesKingCheck(board.getPiece(kingX, kingY - 1), king, kingX, kingY) ||
                doesKingCheck(board.getPiece(kingX, kingY + 1), king, kingX, kingY) ||
                doesKingCheck(board.getPiece(kingX - 1, kingY - 1), king, kingX, kingY) ||
                doesKingCheck(board.getPiece(kingX - 1, kingY + 1), king, kingX, kingY) ||
                doesKingCheck(board.getPiece(kingX + 1, kingY - 1), king, kingX, kingY) ||
                doesKingCheck(board.getPiece(kingX + 1, kingY + 1), king, kingX, kingY));
    }

    private boolean doesKingCheck(Piece piece, Piece king, int x, int y) {
        return piece != null && piece.isWhite() != king.isWhite() && piece instanceof King;
    }

    private boolean hitByPawn(int x, int y, Piece king, int kingX, int kingY, Move move) {
        final int dir = king.isWhite() ? -1 : 1;
        return doesPawnCheck(board.getPiece(kingX + 1, kingY + dir), king, x, y) ||
                doesPawnCheck(board.getPiece(kingX - 1, kingY + dir), king, x, y);
    }

    private boolean doesPawnCheck(Piece piece, Piece king, int x, int y) {
        return piece != null && piece.isWhite() != king.isWhite() && piece instanceof Pawn;
    }

}
