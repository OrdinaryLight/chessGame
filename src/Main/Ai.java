package Main;

import java.util.ArrayList;

import Pieces.Pawn;
import Pieces.Piece;

public class Ai {
    private Board board;

    public Ai(Board board) {
        this.board = board;
    }

    /**
     * Gets the best move for the current player
     * 
     * @param isWhiteTurn true if it's white's turn, false for black
     * @return the best Move found, or null if no valid moves exist
     */
    public Move getBestMove(boolean isWhiteTurn) {
        ArrayList<Move> possibleMoves = getAllValidMoves(isWhiteTurn);

        if (possibleMoves.isEmpty()) {
            return null; // No valid moves available (checkmate or stalemate)
        }

        Move bestMove = null;
        int bestScore = isWhiteTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : possibleMoves) {
            // Simulate the move
            simulateMove(move);

            // Evaluate the board position after the move
            int score = evaluateBoard(isWhiteTurn);

            // Undo the move
            undoMove(move);

            // Check if this move is better than the current best

            if (isWhiteTurn) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }

    /**
     * Gets all valid moves for the current player
     * 
     * @param isWhiteTurn true if it's white's turn, false for black
     * @return ArrayList of all valid moves
     */
    private ArrayList<Move> getAllValidMoves(boolean isWhiteTurn) {
        ArrayList<Move> validMoves = new ArrayList<>();

        for (Piece piece : board.pieces) {
            if (piece.isWhite() == isWhiteTurn) {
                // Check all possible positions on the board
                for (int x = 0; x < Constants.SIZE; x++) {
                    for (int y = 0; y < Constants.SIZE; y++) {
                        Move move = new Move(board, piece, x, y);
                        if (board.isValidMove(move)) {
                            validMoves.add(move);
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    /**
     * Simulates a move on the board without permanently changing the game state
     * 
     * @param move the move to simulate
     */
    private void simulateMove(Move move) {
        board.makeMove(move);
    }

    /**
     * Undo a simulated move to restore the board state
     * 
     * @param move the move to undo
     */
    private void undoMove(Move move) {
        // Restore the original position
        move.piece.setX(move.x);
        move.piece.setY(move.y);
        move.piece.hasntMoved();

        // Restore captured piece if any
        if (move.capturedPiece != null) {
            board.pieces.add(move.capturedPiece);
            board.pieceGroup.getChildren().add(move.capturedPiece);
        }
    }

    /**
     * Evaluates the current board position
     * 
     * @return an integer score representing how good the position is
     *         Positive scores favor white, negative scores favor black
     */
    private int evaluateBoard(boolean isWhiteTurn) {
        String[] temp = {
                "Do En Passant Moves",
                "Center Knights",
                "Per Bishop Move",
                "Forward Pawns",
                "Check Multiplier",
                "Promotion Multiplier",
                "Material Difference"
        };

        final int dir = isWhiteTurn ? 1 : -1;
        int eval = 0;

        if (Constants.aiRules[Constants.DO_CENTER_PAWNS_IDX]) {
            eval += doCenterPawns(isWhiteTurn);
        }

        return dir * eval;
    }

    private int doCenterPawns(boolean isWhiteTurn) {
        int eval = 0;
        for (int i = 2; i < Constants.SIZE - 2; i++) {
            for (int j = 0; j < Constants.SIZE; j++) {
                final Piece p = board.getPiece(j, i);
                if (p != null && p instanceof Pawn && p.isWhite() == isWhiteTurn) {
                    eval += Constants.CENTER_PAWN_VALUE;
                }
            }
        }
        return eval;
    }
}