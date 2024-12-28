
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
                if (viPiece.getPiece() != null) {
                    pieceGroup.getChildren().add(viPiece);

                    doPieceVisuals(viPiece);
                }

            }
        }

        return root;
    }

    private void doPieceVisuals(viPiece viPiece) {
        viPiece.setOnMousePressed(e -> {
            System.out.println("Mouse clicked at: " + viPiece.getPiece().getX() + ", " + viPiece.getPiece().getY());

            litSquares = board.getMoves(viPiece.getPiece(), litSquares);
            System.out.println(litSquares.size());

            for (Move move : litSquares) {

                litSquaresBoard[move.getEndX()][move.getEndY()].activate(true);
            }

        });

        viPiece.setOnMouseDragged(e -> {
            viPiece.relocate(VisualConstants.TILE_SIZE * Math.floor(e.getSceneX() / VisualConstants.TILE_SIZE),
                    VisualConstants.TILE_SIZE * Math.floor(e.getSceneY() / VisualConstants.TILE_SIZE));
            System.out.println("board X: " + pixelXToBoard(e.getSceneX(), e.getSceneY()));
            System.out.println("board Y: " + pixelYToBoard(e.getSceneX(), e.getSceneY()));

        });

        viPiece.setOnMouseReleased(e -> {
            System.out.println("Mouse released");

            for (Move move : litSquares) {

                litSquaresBoard[move.getEndX()][move.getEndY()].activate(false);
            }
            litSquares.clear();

        });
    }

    private int pixelYToBoard(double x, double y) {
        return BoardConstants.SIZE - (int) (Math.floor(x / VisualConstants.TILE_SIZE));
    }

    private int pixelXToBoard(double x, double y) {
        return (int) (Math.floor(x / VisualConstants.TILE_SIZE)) - 1;
    }

    private int boardXtoVisual(int x, int y) {
        return y;
    }

    private int boardYtoVisual(int x, int y) {
        return BoardConstants.SIZE - 1 - x;
    }

}
