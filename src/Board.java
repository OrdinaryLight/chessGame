import java.util.ArrayList;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
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
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

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
        addPieces();

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

    public void addPieces() {
        pieces.add(new Rook(0, 0, false));
        pieces.add(new Rook(Constants.SIZE - 1, 0, false));

        pieces.add(new Rook(0, Constants.SIZE - 1, true));
        pieces.add(new Rook(Constants.SIZE - 1, Constants.SIZE - 1, true));

        pieces.add(new Knight(1, Constants.SIZE - 1, true));
        pieces.add(new Knight(Constants.SIZE - 2, Constants.SIZE - 1, true));

        pieces.add(new Knight(1, 0, false));
        pieces.add(new Knight(Constants.SIZE - 2, 0, false));

        pieces.add(new Bishop(2, 0, false));
        pieces.add(new Bishop(Constants.SIZE - 3, 0, false));

        pieces.add(new Bishop(2, Constants.SIZE - 1, true));
        pieces.add(new Bishop(Constants.SIZE - 3, Constants.SIZE - 1, true));

        pieces.add(new Queen(3, 0, false));
        pieces.add(new Queen(3, Constants.SIZE - 1, true));

        pieces.add(new King(4, 0, false));
        pieces.add(new King(4, Constants.SIZE - 1, true));

        for (int i = 0; i < Constants.SIZE; i++) {
            pieces.add(new Pawn(i, 1, false));
            pieces.add(new Pawn(i, Constants.SIZE - 2, true));
        }

        pieceGroup.getChildren().addAll(pieces);
    }

}
