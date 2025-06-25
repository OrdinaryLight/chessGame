package Main;

import java.util.ArrayList;

import Pieces.Piece;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Input {
    private Board board;
    private boolean isWhiteTurn;
    private App app;
    private Ai ai;

    Input(Board board, boolean isWhiteTurn, App app) {
        this.board = board;
        this.isWhiteTurn = isWhiteTurn;
        this.app = app;
        this.ai = new Ai(board);
        setUpPieces();
    }

    public boolean advanceTurn() {
        isWhiteTurn = !isWhiteTurn;
        doAiMove();

        return isWhiteTurn;
    }

    private void setUpPieces() {
        final ArrayList<Piece> pieces = board.pieces;

        for (Piece p : pieces) {
            doOnMousePressed(p);
            doOnMouseDragged(p);
            doOnMouseReleased(p);
        }
    }

    public void addPiece(Piece p) {
        doOnMousePressed(p);
        doOnMouseDragged(p);
        doOnMouseReleased(p);
    }

    /**
     * when mouse is pressed do some checks and activate squares that are movable to
     * 
     * @param piece the Piece
     */
    private void doOnMousePressed(Piece piece) {
        piece.setOnMousePressed(_ -> {
            if (isWhiteTurn == piece.isWhite() && (isWhiteTurn ? !Constants.aiRules[Constants.DO_WHITE_AI_IDX]
                    : !Constants.aiRules[Constants.DO_BLACK_AI_IDX])) {
                board.activateLitSquares(piece);
            }
        });
    }

    /**
     * move clicked on piece to where we are if the piece is of the same color as
     * our turn
     * 
     * @param piece the piece
     */
    private void doOnMouseDragged(Piece piece) {
        piece.setOnMouseDragged(e -> {
            if (piece.isWhite() == isWhiteTurn && (isWhiteTurn ? !Constants.aiRules[Constants.DO_WHITE_AI_IDX]
                    : !Constants.aiRules[Constants.DO_BLACK_AI_IDX])) {
                piece.relocate(boardSnapX(e.getSceneX(), e.getSceneY()),
                        (boardSnapY(e.getSceneX(), e.getSceneY())));
            }

        });
    }

    private int boardSnapX(double x, double y) {
        return Constants.TILE_SIZE * Math.floorDiv((int) Math.floor(x), Constants.TILE_SIZE);
    }

    private int boardSnapY(double x, double y) {
        return Constants.TILE_SIZE * Math.floorDiv((int) Math.floor(y), Constants.TILE_SIZE);

    }

    /**
     * disable all lit squares. preform move if possible and check if game is over.
     * 
     * @param piece the piece
     */
    private void doOnMouseReleased(Piece piece) {

        piece.setOnMouseReleased(e -> {
            board.removeLitSquares();

            final int newX = boardSnapX(e.getSceneX(), e.getSceneY()) - 1;
            final int newY = boardSnapY(e.getSceneX(), e.getSceneY()) - 1;
            final Move move = new Move(board, piece, Constants.pixelToBoard(newX), Constants.pixelToBoard(newY));

            if (board.isValidMove(move) && isWhiteTurn == piece.isWhite() && (isWhiteTurn
                    ? !Constants.aiRules[Constants.DO_WHITE_AI_IDX]
                    : !Constants.aiRules[Constants.DO_BLACK_AI_IDX])) {
                piece.relocatePiece(Constants.pixelToBoard(newX), Constants.pixelToBoard(newY));
                board.makeMove(move);
                afterMoveLogic(move);
                advanceTurn();
            } else {
                piece.relocatePiece(piece.getX(), piece.getY());
            }
        });

    }

    private void afterMoveLogic(Move move) {
        if (board.checkChecker.isGameOver(board.getKing(isWhiteTurn))) {
            app.checkMate();
            System.out.print("mate");
        }

    }

    public void doAiMove() {
        if ((isWhiteTurn ? Constants.aiRules[Constants.DO_WHITE_AI_IDX]
                : Constants.aiRules[Constants.DO_BLACK_AI_IDX])
                && !board.checkChecker.isGameOver(board.getKing(isWhiteTurn))) {
            final long startTime = System.currentTimeMillis();
            final Move move = ai.getBestMove(isWhiteTurn);
            board.makeMove(move);

            final long timeElapsed = System.currentTimeMillis() - startTime;
            final long remainingTime = 2000 - timeElapsed;

            PauseTransition pause = new PauseTransition(Duration.millis(remainingTime));
            pause.setOnFinished(_ -> {
                afterMoveLogic(move);
                advanceTurn();
            });
            pause.play();

        }
    }

    // Replace your while loop with this method call:
    // executeNextAIMove();

    public void executeNextAIMove() {
        // Same condition as your while loop
        if ((isWhiteTurn ? Constants.aiRules[Constants.DO_WHITE_AI_IDX]
                : Constants.aiRules[Constants.DO_BLACK_AI_IDX])
                && !board.checkChecker.isGameOver(board.getKing(isWhiteTurn))) {

            final long startTime = System.currentTimeMillis();
            final Move move = ai.getBestMove(isWhiteTurn);

            System.out.println("here");

            // This calls relocate(x, y) and the StackPane moves immediately!
            board.makeMove(move);

            final long timeElapsed = System.currentTimeMillis() - startTime;
            final long remainingTime = Math.max(0, 2000 - timeElapsed);

            // Instead of Thread.sleep(), use PauseTransition
            PauseTransition pause = new PauseTransition(Duration.millis(remainingTime));
            pause.setOnFinished(_ -> {
                afterMoveLogic(move);
                advanceTurn();
            });
            pause.play();
        }
        // If condition is false, loop naturally ends (like your while loop)
    }

}
