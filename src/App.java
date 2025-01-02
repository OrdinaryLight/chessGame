
import java.util.ArrayList;

import Constants.BoardConstants;
import Constants.VisualConstants;
import GameplayLogic.Board;
import GameplayLogic.Move;
import GameplayLogic.Pieces.Pawn;
import GameplayLogic.Pieces.Piece;
import GameplayLogic.Pieces.Queen;
import VisualLogic.viBorder;
import VisualLogic.viChessTile;
import VisualLogic.viLitTile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//////////
/// Main class, main connection of visual and logic
/// *Used as such
/// 
public class App extends Application {
    public Board board = new Board();
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private ArrayList<Move> litSquares = new ArrayList<Move>();
    private Group litSquaresGroup = new Group();
    private viLitTile litSquaresBoard[][] = new viLitTile[BoardConstants.SIZE][BoardConstants.SIZE];
    private viBorder border = new viBorder();
    private boolean isWhiteTurn = true;

    public void start(Stage primaryStage) {
        Scene scene = new Scene(makeBoard());

        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * makes the screen/window including the board
     * 
     * @return the screen
     */
    public Pane makeBoard() {
        Pane root = new Pane();
        root.setPrefSize(2 * VisualConstants.X_OFFSET + VisualConstants.TILE_SIZE * BoardConstants.SIZE,
                2 * VisualConstants.Y_OFFSET + VisualConstants.TILE_SIZE * BoardConstants.SIZE);
        root.getChildren().addAll(border, tileGroup, litSquaresGroup, pieceGroup);
        root.setBackground(new Background(new BackgroundFill(VisualConstants.BACKGROUND1, null, null)));

        for (int row = 0; row < BoardConstants.SIZE; row++) {
            for (int col = 0; col < BoardConstants.SIZE; col++) {
                int visualX = boardXtoVisual(row, col);
                int visualY = boardYtoVisual(row, col);

                viChessTile tile = new viChessTile((row + col) % 2 == 0, visualX, visualY);
                tileGroup.getChildren().add(tile);

                viLitTile litTile = new viLitTile(visualX, visualY);
                litSquaresBoard[row][col] = litTile;
                litSquaresGroup.getChildren().add(litTile);

                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    doPieceVisuals(piece);
                }

            }
        }

        return root;
    }

    /**
     * logic for the Pieces (visual and what happens, when click/drag/release
     * pieces)
     * 
     * @param piece the Piece
     */
    private void doPieceVisuals(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        pieceGroup.getChildren().add(piece);
        piece.relocatePiece(boardXtoVisual(x, y), boardYtoVisual(x, y));
        doOnMousePressed(piece);
        doOnMouseDragged(piece);
        doOnMouseReleased(piece);
    }

    /**
     * when mouse is pressed do some checks and activate squares that are movable to
     * 
     * @param piece the Piece
     */
    private void doOnMousePressed(Piece piece) {
        piece.setOnMousePressed(e -> {
            if (board.getPiece(piece.getX(), piece.getY()) != piece) { // ifs should not be activated this is for
                                                                       // testing mainly
                if (board.getPiece(piece.getX(), piece.getY()) == null) {
                    System.out.println("no board piece");
                }
                System.out.println("Ruh oh, boards !=");
            }
            activateLitSquares(piece);

        });
    }

    /**
     * gets the squares the piece can move to and highlight them
     * 
     * @param piece the piece
     */
    private void activateLitSquares(Piece piece) {
        if (piece.isWhite() == isWhiteTurn) {
            litSquares = board.getMoves(piece, litSquares);

            for (Move move : litSquares) {

                litSquaresBoard[move.getEndX()][move.getEndY()].activate(true);
            }
            System.out.println(litSquares.size());
        }
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

    /**
     * disable all lit squares. preform move if possible and check if game is over.
     * 
     * @param piece the piece
     */
    private void doOnMouseReleased(Piece piece) {
        piece.setOnMouseReleased(e -> {
            for (Move move : litSquares) {

                litSquaresBoard[move.getEndX()][move.getEndY()].activate(false);
            }
            litSquares.clear();

            if (tryMove(piece, boardSnapX(e.getSceneX(), e.getSceneY()), boardSnapY(e.getSceneX(), e.getSceneY()))) {
                if (board.isGameOver(isWhiteTurn)) {
                    // TODO checkmate visuals
                    System.out.println("Mate");
                }
            }
        });
    }

    /**
     * given pixel coords, return a coord of the nearest tile
     * 
     * @param pixelX given Y
     * @param pixelY given X
     * @return new Y
     */
    private int boardSnapY(double pixelX, double pixelY) {
        return VisualConstants.TILE_SIZE * (int) Math.floor(pixelY / VisualConstants.TILE_SIZE);
    }

    /**
     * given pixel coords, return a coord of the nearest tile
     * 
     * @param pixelX given Y
     * @param pixelY given X
     * @return new X
     */
    private int boardSnapX(double pixelX, double pixelY) {
        return VisualConstants.TILE_SIZE * (int) Math.floor(pixelX / VisualConstants.TILE_SIZE);
    }

    /**
     * what the index of a piece on the board is different then the index of the
     * display because i made the board first
     * 
     */
    private int boardXtoVisual(int x, int y) {
        return x;
    }

    /**
     * what the index of a piece on the board is different then the index of the
     * display because i made the board first
     * 
     */
    private int boardYtoVisual(int x, int y) {
        return BoardConstants.SIZE - 1 - y;
    }

    /**
     * given pixel coords, return coord of board class representing pixel
     * 
     * @param pixelX given Y
     * @param pixelY given X
     * @return new X
     */
    private int pixelXToBoard(double pixelX, double pixelY) {
        return (int) (pixelX - VisualConstants.X_OFFSET) / VisualConstants.TILE_SIZE;
    }

    /**
     * given pixel coords, return coord of board class representing pixel
     * 
     * @param pixelX given Y
     * @param pixelY given X
     * @return new Y
     */
    private int pixelYToBoard(double pixelX, double pixelY) {
        return BoardConstants.SIZE - 1 - (int) (pixelY - VisualConstants.Y_OFFSET) / VisualConstants.TILE_SIZE;
    }

    /**
     * 
     * 
     * @param piece
     * @param pixelX
     * @param pixelY
     * @return
     */
    public boolean tryMove(Piece piece, int pixelX, int pixelY) {
        int newX = pixelXToBoard(pixelX, pixelY);
        int newY = pixelYToBoard(pixelX, pixelY);
        int oldX = piece.getX();
        int oldY = piece.getY();
        ArrayList<Move> movesList = new ArrayList<Move>();

        if (board.isWithinBoard(newX, newY) && piece.isWhite() == isWhiteTurn) {
            Piece targetPiece = board.getPiece(newX, newY);
            Move attemptedMove = new Move(oldX, oldY, newX, newY, piece, targetPiece);
            movesList = board.getMoves(piece, movesList);

            if (tempHasMoves(movesList, attemptedMove) && board.doPlayerMove(oldX, oldY, newX, newY)) {
                isWhiteTurn = !isWhiteTurn; // valid move change who's turn it is
                if (targetPiece != null) {
                    deletePiece(targetPiece);
                    System.out.println("Captured"); // Testing
                } else {
                    System.out.println("Moved"); // Testing
                }

                // promotion (auto queen rn and prolly for ever cause im lazy tbh)
                if ((newY == 0 || newY == BoardConstants.SIZE - 1) && board.getPiece(newX, newY) instanceof Pawn) {
                    promote(newX, newY, !isWhiteTurn);
                }

                return true;
            } else {
                piece.relocatePiece(boardXtoVisual(oldX, oldY), boardYtoVisual(oldX, oldY));
                System.out.println("Not moved1"); // Testing
            }
        } else {
            piece.relocatePiece(boardXtoVisual(oldX, oldY), boardYtoVisual(oldX, oldY));
            System.out.println("Not moved2"); // Testing
        }
        return false;

    }

    /**
     * Check to see if a move is valid
     * 
     * @param movesList     list of valid moves
     * @param attemptedMove move that is being attempted
     * @return whether or not the move is valid
     */
    public boolean tempHasMoves(ArrayList<Move> movesList, Move attemptedMove) {
        for (Move move : movesList) {
            if (move.equals(attemptedMove)) {
                return true;
            }
        }

        return false;
    }

    /**
     * deletes a Piece
     * 
     * @param piece the piece to delete
     */
    private void deletePiece(Piece piece) {
        pieceGroup.getChildren().remove(piece);
        piece = null;
    }

    /**
     * Promotes a piece to a queen (would be better to put this in board class)
     * 
     * @param x       x coord of piece
     * @param y       y coord of piece
     * @param isWhite the color of the piece
     */
    private void promote(int x, int y, boolean isWhite) {
        Piece oldPawn = new Queen(x, y, isWhite);
        deletePiece(board.getPiece(x, y));
        board.setPiece(x, y, oldPawn);
        doPieceVisuals(oldPawn);

    }

}
