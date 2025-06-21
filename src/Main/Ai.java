package Main;

import java.util.ArrayList;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;

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
        boolean lastMovePromoted = board.justPromoted;

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
            undoMove(move, lastMovePromoted);

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
    private void undoMove(Move move, boolean lastMovePromoted) {
        // Restore the original position
        move.piece.setX(move.x);
        move.piece.setY(move.y);
        move.piece.hasntMoved();

        // Restore captured piece if any
        if (move.capturedPiece != null) {
            board.pieces.add(move.capturedPiece);
            board.pieceGroup.getChildren().add(move.capturedPiece);
        }

        // restore promotions
        if (board.justPromoted) {
            board.pieces.add(move.piece);
            board.pieceGroup.getChildren().add(move.piece);
            board.justPromoted = lastMovePromoted;
        }
    }

    /**
     * Evaluates the current board position
     * 
     * @return an integer score representing how good the position is
     *         Positive scores favor white, negative scores favor black
     */
    private int evaluateBoard(boolean isWhiteTurn) {

        final int dir = isWhiteTurn ? 1 : -1;
        int eval = 0;

        if (Constants.aiRules[Constants.DO_CENTER_PAWNS_IDX]) {
            eval += doCenterPawns(isWhiteTurn);
        }

        if (Constants.aiRules[Constants.DO_ENPASSANTS_IDX]) {
            eval += doEnPassantMoves(isWhiteTurn);
        }

        if (Constants.aiRules[Constants.DO_CENTER_KNIGHTS_IDX]) {
            eval += doCenterKnights(isWhiteTurn);
        }

        if (Constants.aiRules[Constants.DO_PER_BISHOP_MOVE_IDX]) {
            eval += doPerBishopMove(isWhiteTurn);
        }

        if (Constants.aiRules[Constants.DO_FORWARD_PAWNS_IDX]) {
            eval += doForwardPawns(isWhiteTurn);
        }

        if (Constants.aiRules[Constants.DO_MATERIAL_DIFFERENCE_IDX]) {
            eval += doMaterialDifference(isWhiteTurn);
        }

        if (Constants.aiRules[Constants.DO_PROMOTION_MULTIPLIER_IDX]) {
            eval *= doPromotionMultiplier(isWhiteTurn);
        }

        if (Constants.aiRules[Constants.DO_CHECK_MULTIPLIER_IDX]) {
            eval *= doCheckMultiplier(isWhiteTurn);
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

    private int doEnPassantMoves(boolean isWhiteTurn) {
        int eval = 0;
        final int dir = isWhiteTurn ? -1 : 1;

        for (Piece p : board.pieces) {
            if (p instanceof Pawn && p.isWhite() == isWhiteTurn) {
                final int enPassantSquare = board.getEnPassantSquare();

                if (enPassantSquare != -1) {
                    final int enPassantX = enPassantSquare % Constants.SIZE;
                    final int enPassantY = enPassantSquare / Constants.SIZE;

                    if (Math.abs(p.getX() - enPassantX) == 1 && p.getY() + dir == enPassantY) {
                        eval += Constants.ENPASSANT_POINTS;
                    }
                }
            }
        }
        return eval;
    }

    private int doCenterKnights(boolean isWhiteTurn) {
        int eval = 0;
        for (int i = 2; i < Constants.SIZE - 2; i++) {
            for (int j = 1; j < Constants.SIZE - 1; j++) {
                final Piece p = board.getPiece(j, i);
                if (p != null && p instanceof Knight && p.isWhite() == isWhiteTurn) {
                    eval += Constants.CENTER_KNIGHT_VALUE;
                }
            }
        }
        return eval;
    }

    private int doPerBishopMove(boolean isWhiteTurn) {
        int eval = 0;
        for (Piece p : board.pieces) {
            if (p instanceof Bishop && p.isWhite() == isWhiteTurn) {
                int moveCount = 0;

                final int[][] directions = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
                for (int[] dir : directions) {
                    for (int distance = 1; distance < Constants.SIZE; distance++) {
                        int newX = p.getX() + dir[0] * distance;
                        int newY = p.getY() + dir[1] * distance;

                        if (newX < 0 || newX >= Constants.SIZE || newY < 0 || newY >= Constants.SIZE) {
                            break;
                        }

                        Move testMove = new Move(board, p, newX, newY);
                        if (board.isValidMove(testMove)) {
                            moveCount++;
                        }

                        if (board.getPiece(newX, newY) != null) {
                            break;
                        }
                    }
                }
                eval += moveCount * Constants.PER_BISHOP_MOVE_POINT;
            }
        }
        return eval;
    }

    private int doForwardPawns(boolean isWhiteTurn) {
        int eval = 0;

        for (Piece p : board.pieces) {
            if (p instanceof Pawn && p.isWhite() == isWhiteTurn) {
                int advancement = 0;
                if (isWhiteTurn) {
                    advancement = Constants.SIZE - 6 - p.getY();
                } else {
                    advancement = p.getY() - 1;
                }

                if (advancement > 0) {
                    eval += advancement * Constants.FORWARD_PAWN_MULTIPLIER;
                }
            }
        }
        return eval;
    }

    private int doCheckMultiplier(boolean isWhiteTurn) {
        Piece enemyKing = board.getKing(!isWhiteTurn);

        for (Piece p : board.pieces) {
            if (p.isWhite() == isWhiteTurn) {
                if (p.isValidMove(enemyKing.getX(), enemyKing.getY())) {
                    return Constants.DO_CHECK_MULTIPLIER_IDX;
                }
            }
        }
        return 1;
    }

    private int doPromotionMultiplier(boolean isWhiteTurn) { // need a way of checking promotions
        return board.justPromoted ? Constants.DO_PROMOTION_MULTIPLIER_IDX : 1;
    }

    private int doMaterialDifference(boolean isWhiteTurn) {
        int ourMaterial = 0;
        int theirMaterial = 0;

        for (Piece p : board.pieces) {
            int pieceValue = 0;
            if (p instanceof Pawn) {
                pieceValue = Constants.PAWN_VALUE;
            } else if (p instanceof Knight) {
                pieceValue = Constants.KNIGHT_VALUE;
            } else if (p instanceof Bishop) {
                pieceValue = Constants.BISHOP_VALUE;
            } else if (p instanceof Rook) {
                pieceValue = Constants.ROOK_VALUE;
            } else if (p instanceof Queen) {
                pieceValue = Constants.QUEEN_VALUE;
            } else if (p instanceof King) {
                pieceValue = Constants.KING_VALUE;
            }

            if (p.isWhite() == isWhiteTurn) {
                ourMaterial += pieceValue;
            } else {
                theirMaterial += pieceValue;
            }
        }

        return ourMaterial - theirMaterial;
    }
}