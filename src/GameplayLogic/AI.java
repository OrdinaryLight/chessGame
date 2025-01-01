package GameplayLogic;

import java.util.List;

import Constants.BoardConstants;

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
    public Move aiMove(Board board, boolean isWhiteTurn) {
        return minimax(board, BoardConstants.MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, isWhiteTurn).getMove();
    }

    /**
     * 
     * @param board
     * @param depth
     * @param alpha
     * @param beta
     * @param isMaximizingPlayer
     * @return
     */
    private MinimaxResult minimax(Board board, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || board.isGameOver(isMaximizingPlayer)) {
            return new MinimaxResult(evaluateBoard(board, isMaximizingPlayer), null);
        }

        List<Move> legalMoves = board.generateAllLegalMoves(isMaximizingPlayer);
        Move bestMove = null;

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : legalMoves) {
                board.movePiece(move.getStartX(), move.getStartY(), move.getEndX(), move.getEndY());
                int eval = minimax(board, depth - 1, alpha, beta, false).getEvaluation();
                undoMove(board, move); // Ensure you have a method to undo moves
                if (eval > maxEval) {
                    maxEval = eval;
                    bestMove = move;
                }
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return new MinimaxResult(maxEval, bestMove);
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : legalMoves) {
                board.movePiece(move.getStartX(), move.getStartY(), move.getEndX(), move.getEndY());
                int eval = minimax(board, depth - 1, alpha, beta, true).getEvaluation();
                undoMove(board, move); // Ensure you have a method to undo moves
                if (eval < minEval) {
                    minEval = eval;
                    bestMove = move;
                }
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return new MinimaxResult(minEval, bestMove);
        }
    }

    private int evaluateBoard(Board board, boolean isMaximizingPlayer) {
        int evaluation = 0;
        // Implement your evaluation logic here
        // For example, material balance, positional advantages, etc.
        return evaluation;
    }

    private class MinimaxResult {
        private final int evaluation;
        private final Move move;

        public MinimaxResult(int evaluation, Move move) {
            this.evaluation = evaluation;
            this.move = move;
        }

        public int getEvaluation() {
            return evaluation;
        }

        public Move getMove() {
            return move;
        }
    }

    public void undoMove(Board board, Move move) {
        board.setPiece(move.getStartX(), move.getStartY(), move.getMovingPiece());
        board.setPiece(move.getEndX(), move.getEndY(), move.getCapturedPiece());
    }

}
