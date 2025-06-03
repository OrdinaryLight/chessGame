package Main;

import java.util.ArrayList;

import Pieces.Bishop;
import Pieces.Constants;
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
    Group tileGroup = new Group();
    Group litSquaresGroup = new Group();
    viLitTile litSquaresBoard[][] = new viLitTile[Constants.SIZE][Constants.SIZE];
    Group pieceGroup = new Group();
    viBorder border = new viBorder();
    Pane board = new Pane();
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    ArrayList<viLitTile> litSquares = new ArrayList<viLitTile>();
    int enPassantSquare;
    Checks checkChecker;

    public Board() {
        this.board = makeBoard();
        this.enPassantSquare = -1;
        this.checkChecker = new Checks(this);
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
        pieces.add(new Rook(0, 0, false, this));
        pieces.add(new Rook(Constants.SIZE - 1, 0, false, this));

        pieces.add(new Rook(0, Constants.SIZE - 1, true, this));
        pieces.add(new Rook(Constants.SIZE - 1, Constants.SIZE - 1, true, this));

        pieces.add(new Knight(1, Constants.SIZE - 1, true, this));
        pieces.add(new Knight(Constants.SIZE - 2, Constants.SIZE - 1, true, this));

        pieces.add(new Knight(1, 0, false, this));
        pieces.add(new Knight(Constants.SIZE - 2, 0, false, this));

        pieces.add(new Bishop(2, 0, false, this));
        pieces.add(new Bishop(Constants.SIZE - 3, 0, false, this));

        pieces.add(new Bishop(2, Constants.SIZE - 1, true, this));
        pieces.add(new Bishop(Constants.SIZE - 3, Constants.SIZE - 1, true, this));

        pieces.add(new Queen(3, 0, false, this));
        pieces.add(new Queen(3, Constants.SIZE - 1, true, this));

        pieces.add(new King(4, 0, false, this));
        pieces.add(new King(4, Constants.SIZE - 1, true, this));

        for (int i = 0; i < Constants.SIZE; i++) {
            pieces.add(new Pawn(i, 1, false, this));
            pieces.add(new Pawn(i, Constants.SIZE - 2, true, this));
        }

        pieceGroup.getChildren().addAll(pieces);
    }

    public Piece getPiece(int x, int y) {
        for (Piece i : pieces) {
            if (i.getY() == y && i.getX() == x) {
                return i;
            }
        }
        return null;
    }

    public int getTileNum(int x, int y) {
        return y * Constants.SIZE + x;
    }

    public int getEnPassantSquare() {
        return enPassantSquare;
    }

    public Piece getKing(boolean isWhite) {
        for (Piece p : pieces) {
            if (p instanceof King && p.isWhite() == isWhite) {
                return p;
            }
        }

        throw new Error("no king found when attempting");
    }

    public Checks getCheckChecker() {
        return checkChecker;
    }

    public void activateLitSquares(Piece piece) {
        for (int i = 0; i < Constants.SIZE; i++) {
            for (int j = 0; j < Constants.SIZE; j++) {
                if (isValidMove(new Move(this, piece, i, j))) {
                    litSquaresBoard[j][i].activate(true);
                    litSquares.add(litSquaresBoard[j][i]);
                }
                if (piece.getX() == i && piece.getY() == j) {
                    if (isValidMove(new Move(this, piece, i, j))) {
                        System.out.println("ol");
                        System.out.println(i + " " + j);
                    } else {
                        System.out.println("better");
                        System.out.println(i + " " + j);

                    }
                }

            }
        }
    }

    public void removeLitSquares() {
        for (viLitTile v : litSquares) {
            v.activate(false);
        }
        litSquares.clear();
    }

    public boolean isValidMove(Move move) {
        if (isSameTeam(move.piece, move.capturedPiece)) {
            return false;
        }

        if (!move.piece.isValidMove(move.newX, move.newY)) {
            return false;
        }

        if (move.piece.moveCollides(move.newX, move.newY)) {
            return false;
        }

        if (checkChecker.isKingChecked(move)) {
            return false;
        }

        return true;
    }

    public boolean isSameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite() == p2.isWhite();
    }

    public void makeMove(Move move) {

        if (move.piece instanceof Pawn) {
            movePawn(move);
        } else if (move.piece instanceof King) {
            moveKing(move);
        } else {
            move.piece.setX(move.newX);
            move.piece.setY(move.newY);
            move.piece.hasMoved();
            capture(move.capturedPiece);
        }

    }

    private void capture(Piece piece) {
        pieces.remove(piece);
        pieceGroup.getChildren().remove(piece);

    }

    private void promotePawn(Piece pawn) {
        final int x = pawn.getX();
        final int y = pawn.getY();
        final boolean isWhite = pawn.isWhite();
        final Piece queen = new Queen(x, y, isWhite, this);

        capture(pawn);
        pieceGroup.getChildren().add(queen);
        pieces.add(queen);

    }

    private void movePawn(Move move) {
        final int dir = move.piece.isWhite() ? 1 : -1;

        if (enPassantSquare == getTileNum(move.newX, move.newY)) {
            move.capturedPiece = getPiece(move.newX, move.newY + dir);
        }

        if (Math.abs(move.newY - move.y) == 2) {
            enPassantSquare = getTileNum(move.newX, move.newY + dir);
        } else {
            enPassantSquare = -1;
        }

        if (move.piece.isWhite() && move.newY == 0 || !move.piece.isWhite() && move.newY == 7) {
            promotePawn(move.piece);
        }

        move.piece.setX(move.newX);
        move.piece.setY(move.newY);
        move.piece.hasMoved();
        capture(move.capturedPiece);
    }

    private void moveKing(Move move) {
        if (Math.abs(move.newX - move.x) > 1) {
            final Piece rook;
            if (move.x > move.newX) {
                rook = getPiece(0, move.y);

                rook.setX(3);
                rook.hasMoved();
                move.piece.setX(move.newX);
                move.piece.setY(move.newY);
                move.piece.hasMoved();
            } else {
                rook = getPiece(7, move.y);

                rook.setX(5);
                rook.hasMoved();
                move.piece.setX(move.newX);
                move.piece.setY(move.newY);
                move.piece.hasMoved();
            }
            rook.relocatePiece(rook.getX(), rook.getY());
        } else {
            move.piece.setX(move.newX);
            move.piece.setY(move.newY);
            move.piece.hasMoved();
            capture(move.capturedPiece);
        }
    }

}
