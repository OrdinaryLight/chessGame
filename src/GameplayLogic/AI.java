package GameplayLogic;

import java.util.ArrayList;
import java.util.List;

import Constants.BoardConstants;
import GameplayLogic.Pieces.Bishop;
import GameplayLogic.Pieces.Knight;
import GameplayLogic.Pieces.Pawn;
import GameplayLogic.Pieces.Piece;

//////////
/// Class used to calculate an Ai's next turn
/// *Used to seperate what logic the board should do and what logic the Ai should do
/// 
public class AI {

    /**
     * calculates and gets the best Move for the given position
     * 
     * @param board       the current state of the board
     * @param isWhiteTurn who's turn it is
     * @return the best Move
     */
    public static Move aiMove(Board board, boolean isWhiteTurn, Move move) {
        Move temp = minimax(board, BoardConstants.MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, isWhiteTurn, move)
                .getMove();
        return temp;
    }

    /**
     * Minimax algorithm with alpha-beta pruning to find the best move
     * 
     * @param board              The current state of the chess board
     * @param depth              The current depth in the search tree (decreases
     *                           with each recursive call)
     * @param alpha              The best score that the maximizing player can
     *                           guarantee
     * @param beta               The best score that the minimizing player can
     *                           guarantee
     * @param isMaximizingPlayer Whether the current player is maximizing (true) or
     *                           minimizing (false)
     * @param lastMove           The last move that was made to reach this board
     *                           state
     * @return A MinimaxResult containing the evaluation and the best move found
     */
    private static MinimaxResult minimax(Board board, int depth, int alpha, int beta, boolean isMaximizingPlayer,
            Move lastMove) {
        // Base case: reached maximum depth or game is over
        if (depth == 0 || board.isGameOver(isMaximizingPlayer)) {
            return new MinimaxResult(evaluateBoard(board, isMaximizingPlayer, lastMove), lastMove);
        }

        List<Move> legalMoves = board.generateAllLegalMoves(isMaximizingPlayer);

        // If no legal moves are available but game isn't over, return current
        // evaluation
        if (legalMoves.isEmpty()) {
            return new MinimaxResult(evaluateBoard(board, isMaximizingPlayer, lastMove), null);
        }

        Move bestMove = null;

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : legalMoves) {
                // Make the move
                board.movePiece(move);

                // Recursively evaluate the position
                int eval = minimax(board, depth - 1, alpha, beta, false, move).getEvaluation();

                // Undo the move to restore the board
                undoMove(board, move);

                // Update best move if better evaluation is found
                if (eval > maxEval) {
                    maxEval = eval;
                    bestMove = move;
                }

                // Alpha-beta pruning
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Beta cut-off
                }
            }
            return new MinimaxResult(maxEval, bestMove);
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : legalMoves) {
                // Make the move
                board.movePiece(move);

                // Recursively evaluate the position
                int eval = minimax(board, depth - 1, alpha, beta, true, move).getEvaluation();

                // Undo the move to restore the board
                undoMove(board, move);

                // Update best move if better evaluation is found
                if (eval < minEval) {
                    minEval = eval;
                    bestMove = move;
                }

                // Alpha-beta pruning
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }
            return new MinimaxResult(minEval, bestMove);
        }
    }

    // rules to implement still
    public static final boolean DO_PROMOTION_MULTIPLIER = true;
    public static final boolean DO_MATERIAL_DIFFERENCE = true;

    private static int evaluateBoard(Board board, boolean isMaximizingPlayer, Move move) {
        int evaluation = 0;

        evaluation += doEnpassants(move);

        for (int x = 0; x < BoardConstants.SIZE; x++) {
            for (int y = 0; y < BoardConstants.SIZE; y++) {
                Piece piece = board.getPiece(x, y);
                evaluation += (doCenterPawnsEval(isMaximizingPlayer, board, piece));
                evaluation += (doCenterKnightsEval(isMaximizingPlayer, board, piece));
                evaluation += (doPerBishopMoveEval(board, piece));
                evaluation += (doForwardPawns(move));

            }
        }

        evaluation *= doCheckMultiplier(board, isMaximizingPlayer);

        return isMaximizingPlayer ? evaluation : -evaluation;
    }

    private static int doCheckMultiplier(Board board, boolean isWhite) {
        if (board.isKingInCheck(isWhite)) {
            return BoardConstants.CHECK_MULTIPLIER;
        }
        return 1;
    }

    private static int doCenterPawnsEval(boolean isWhite, Board board, Piece piece) {
        if (piece != null && piece instanceof Pawn && piece.isWhite() == isWhite
                && (piece.getX() > 1 && piece.getY() < BoardConstants.SIZE - 2)) {
            return BoardConstants.CENTER_PAWN_VALUE;
        }
        return 0;
    }

    private static int doCenterKnightsEval(boolean isWhite, Board board, Piece piece) {
        if (piece != null && piece instanceof Knight && piece.isWhite() == isWhite
                && (piece.getX() > 1 && piece.getY() < BoardConstants.SIZE - 2)) {
            return BoardConstants.CENTER_KNIGHT_VALUE;
        }
        return 0;
    }

    private static int doPerBishopMoveEval(Board board, Piece piece) {
        if (piece instanceof Bishop) {
            ArrayList<Move> moves = new ArrayList<Move>();
            piece.getMoves(moves, board);
            return BoardConstants.PER_BISHOP_MOVE_POINT * moves.size();
        }
        return 0;
    }

    private static int doEnpassants(Move move) {
        if (move.getCapturedPiece() == null) {
            return 0;
        }
        if (move.getEndY() != move.getCapturedPiece().getY()) {
            return BoardConstants.ENPASSANT_POINTS;
        }
        return 0;
    }

    private static int doForwardPawns(Move move) {
        if (move.getMovingPiece() instanceof Pawn) {
            final int SPOTS_FORWARD = move.getMovingPiece().isWhite() ? move.getEndY()
                    : BoardConstants.SIZE - 1 - move.getEndY();
            return BoardConstants.FORWARD_PAWN_MULTIPLIER * SPOTS_FORWARD;
        }
        return 0;
    }

    // make in board class
    public static void undoMove(Board board, Move move) {
        board.setPiece(move.getStartX(), move.getStartY(), move.getMovingPiece());
        board.setPiece(move.getEndX(), move.getEndY(), move.getCapturedPiece());
    }

}
