import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;

//////////
/// Class is used to preform tasks on and be a chessBoard
/// *Used as such
/// ***Seperating some logic out may be helpful.
/// 
public class Board {
    private Group tileGroup = new Group();
    private Group litSquaresGroup = new Group();
    private viLitTile litSquaresBoard[][] = new viLitTile[Constants.SIZE][Constants.SIZE];
    private Group pieceGroup = new Group();
    private viBorder border = new viBorder();
    private Pane board = new Pane();

    public Pane getBoard() {
        return board;
    }

    public Board() {
        this.board = makeBoard();
    }

    /**
     * makes the screen/window including the board
     * 
     * @return the screen
     */
    public Pane makeBoard() {
        Pane root = new Pane();
        root.setPrefSize(2 * Constants.X_OFFSET + Constants.TILE_SIZE * Constants.SIZE,
                2 * Constants.Y_OFFSET + Constants.TILE_SIZE * Constants.SIZE);
        root.getChildren().addAll(border, tileGroup, litSquaresGroup, pieceGroup);
        root.setBackground(new Background(new BackgroundFill(Constants.BACKGROUND1, null, null)));

        for (int row = 0; row < Constants.SIZE; row++) {
            for (int col = 0; col < Constants.SIZE; col++) {

                viChessTile tile = new viChessTile((row + col) % 2 == 0, col, row);
                tileGroup.getChildren().add(tile);

                viLitTile litTile = new viLitTile(col, row);
                litSquaresBoard[row][col] = litTile;
                litSquaresGroup.getChildren().add(litTile);

            }
        }

        return root;
    }

}
