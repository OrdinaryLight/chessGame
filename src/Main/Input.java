package Main;

import java.util.ArrayList;

import Pieces.Piece;

public class Input {
    private Board board;
    private boolean isWhiteTurn;
    private App app;

    Input(Board board, boolean isWhiteTurn, App app) {
        this.board = board;
        this.isWhiteTurn = isWhiteTurn;
        this.app = app;
        setUpPieces();
    }

    public boolean advanceTurn() {
        isWhiteTurn = !isWhiteTurn;
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
            if (isWhiteTurn == piece.isWhite()) {
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
            if (piece.isWhite() == isWhiteTurn) {
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

            if (board.isValidMove(move) && isWhiteTurn == piece.isWhite()) {
                piece.relocatePiece(Constants.pixelToBoard(newX), Constants.pixelToBoard(newY));
                board.makeMove(move);
                afterMoveLogic(move);
            } else {
                piece.relocatePiece(piece.getX(), piece.getY());
            }
        });

    }

    private void afterMoveLogic(Move move) {
        isWhiteTurn = !move.piece.isWhite();

        if (board.checkChecker.isGameOver(board.getKing(isWhiteTurn))) {
            app.checkMate();
            System.out.print("mate");
        }
        ;
    }

}
