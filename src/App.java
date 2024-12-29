
import java.util.ArrayList;

import Constants.BoardConstants;
import Constants.VisualConstants;
import GameplayLogic.Board;
import GameplayLogic.Move;
import VisualLogic.viChessTile;
import VisualLogic.viLitTile;
import VisualLogic.viPiece;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    public Board board = new Board();
    private Group tileGroup = new Group();

    private Group pieceGroup = new Group();
    private viPiece viPieceBoard[][] = new viPiece[BoardConstants.SIZE][BoardConstants.SIZE];

    private ArrayList<Move> litSquares = new ArrayList<Move>();
    private Group litSquaresGroup = new Group();
    private viLitTile litSquaresBoard[][] = new viLitTile[BoardConstants.SIZE][BoardConstants.SIZE];

    public void start(Stage primaryStage) {
        Scene scene = new Scene(makeBoard());

        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Pane makeBoard() {
        Pane root = new Pane();
        root.setPrefSize(2 * VisualConstants.X_OFFSET + VisualConstants.TILE_SIZE * BoardConstants.SIZE,
                2 * VisualConstants.Y_OFFSET + VisualConstants.TILE_SIZE * BoardConstants.SIZE);
        root.getChildren().addAll(tileGroup, litSquaresGroup, pieceGroup);
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

                viPiece viPiece = new viPiece(board.getPiece(row, col), visualX, visualY);
                viPieceBoard[row][col] = viPiece;
                if (viPiece.getPiece() != null) {
                    pieceGroup.getChildren().add(viPiece);

                    doPieceVisuals(viPiece);
                }

            }
        }

        return root;
    }

    private void doPieceVisuals(viPiece viPiece) {
        doOnMousePressed(viPiece);
        doOnMouseDragged(viPiece);
        doOnMouseReleased(viPiece);
    }

    private void doOnMousePressed(viPiece viPiece) {
        viPiece.setOnMousePressed(e -> {
            System.out.println("Mouse clicked at: " + viPiece.getPiece().getX() + ", " + viPiece.getPiece().getY());

            if (board.getPiece(viPiece.getPiece().getX(), viPiece.getPiece().getY()) != viPiece.getPiece()) {
                if (board.getPiece(viPiece.getPiece().getX(), viPiece.getPiece().getY()) == null) {
                    System.out.println("no board piece");
                } else if (viPiece.getPiece() == null) {
                    System.out.println("no visual piece");
                }
                System.out.println("Ruh oh, boards !=");
            }

            litSquares = board.getMoves(viPiece.getPiece(), litSquares);

            for (Move move : litSquares) {

                litSquaresBoard[move.getEndX()][move.getEndY()].activate(true);
            }

        });
    }

    private void doOnMouseDragged(viPiece viPiece) {
        viPiece.setOnMouseDragged(e -> {
            viPiece.relocate(boardSnapX(e.getSceneX(), e.getSceneY()),
                    (boardSnapY(e.getSceneX(), e.getSceneY())));
        });
    }

    private void doOnMouseReleased(viPiece viPiece) {
        viPiece.setOnMouseReleased(e -> {
            System.out.println("Mouse released");

            for (Move move : litSquares) {

                litSquaresBoard[move.getEndX()][move.getEndY()].activate(false);
            }
            litSquares.clear();

            movePiece(viPiece, boardSnapX(e.getSceneX(), e.getSceneY()),
                    boardSnapY(e.getSceneX(), e.getSceneY()));

        });
    }

    private int boardSnapY(double pixelX, double pixelY) {
        return VisualConstants.TILE_SIZE * (int) Math.floor(pixelY / VisualConstants.TILE_SIZE);
    }

    private int boardSnapX(double pixelX, double pixelY) {
        return VisualConstants.TILE_SIZE * (int) Math.floor(pixelX / VisualConstants.TILE_SIZE);
    }

    private int boardXtoVisual(int x, int y) {
        return x;
    }

    private int boardYtoVisual(int x, int y) {
        return BoardConstants.SIZE - 1 - y;
    }

    private int pixelXToBoard(double pixelX, double pixelY) {
        return (int) (pixelX - VisualConstants.X_OFFSET) / VisualConstants.TILE_SIZE;
    }

    private int pixelYToBoard(double pixelX, double pixelY) {
        return BoardConstants.SIZE - 1 - (int) (pixelY - VisualConstants.Y_OFFSET) / VisualConstants.TILE_SIZE;
    }

    private void movePiece(viPiece viPiece, int pixelX, int pixelY) {
        int newX = pixelXToBoard(pixelX, pixelY);
        int newY = pixelYToBoard(pixelX, pixelY);
        int oldX = viPiece.getPiece().getX();
        int oldY = viPiece.getPiece().getY();

        if (board.isWithinBoard(newX, newY)) {
            viPiece targetPiece = viPieceBoard[newX][newY];
            if (!board.movePiece(oldX, oldY, newX, newY)) {
                viPiece.relocatePiece(boardXtoVisual(oldX, oldY), boardYtoVisual(oldX, oldY)); // TODO issues arrising
                                                                                               // to due with not
                                                                                               // updating viBoard,
                                                                                               // should i just move it
                                                                                               // into onie
                System.out.println("Not moved");

            } else {
                if (targetPiece.getPiece() != null) {
                    deletePiece(targetPiece);
                    System.out.println("Captured");
                } else {
                    System.out.println("Moved");
                }
            }
        } else {
            viPiece.relocatePiece(boardXtoVisual(oldX, oldY), boardYtoVisual(oldX, oldY));
            System.out.println("Not moved");
        }

    }

    private void deletePiece(viPiece piece) {
        piece.relocate(0, 0);
        piece.setVisible(false);
        piece = null;
    }

}
