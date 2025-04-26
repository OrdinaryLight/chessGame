package GameplayLogic.Pieces;

import java.util.ArrayList;

import Constants.BoardConstants;
import Constants.VisualConstants;
import GameplayLogic.Board;
import GameplayLogic.Move;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

//////////
/// Class represents a Piece in chess
///*Used as such
/// 
/// 
public abstract class Piece extends StackPane {
    protected int x; // X coordinate
    protected int y; // Y coordinate
    protected boolean isWhite; // color of Piece
    protected ImageView image; // image associated with Piece

    public Piece(int x, int y, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;

        image = new ImageView();
        image.setImage(getImage());
        image.setFitWidth(VisualConstants.TILE_SIZE);
        image.setFitHeight(VisualConstants.TILE_SIZE);
        getChildren().add(image);
    }

    /**
     * checks if a move is inbounds and is a possible move by a piece
     * 
     * @param newX the X the piece will move to
     * @param newY the Y the piece will move to
     * @return whether or not a move is possible
     */
    public abstract boolean isValidMove(int newX, int newY);

    /**
     * gets all legal moves of the piece who called the function
     * 
     * @param moves where to store the moves
     * @param board the board we're using
     * @return moves
     */
    public abstract ArrayList<Move> getMoves(ArrayList<Move> moves, Board board);

    /**
     * adds all legal moves found from a ArrayList of potential moves to another
     * ArrayList
     * 
     * @param potentialMoves List of potential legal moves
     * @param moves          List of current legal moves
     * @param board          The board
     * @return moves
     */
    protected ArrayList<Move> addLegalMoves(ArrayList<Move> potentialMoves, ArrayList<Move> moves, Board board) {
        for (Move move : potentialMoves)
            if (!board.moveCreatesIllegalCheck(move))
                moves.add(move);

        return moves;
    }

    /**
     * checks if a spot can be captured
     * 
     * @param targetX X to move to
     * @param targetY Y to move to
     * @param board   the board
     * @return whether or not the spot can be captured
     */
    protected boolean isCapturable(int targetX, int targetY, Board board) {
        return inBounds(targetX, targetY) && (board.getPiece(targetX, targetY) == null
                || board.getPiece(targetX, targetY).isWhite() != isWhite);
    }

    /**
     * gets the image associated with that piece
     * 
     * @return the Image associated with that piece
     */
    public abstract Image getImage();

    /**
     * gets the pieces X position
     * 
     * @return the position
     */
    public int getX() {
        return x;
    }

    /**
     * sets the pieces X position
     * 
     * @param x the position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * gets the pieces Y position
     * 
     * @return the position
     */
    public int getY() {
        return y;
    }

    /**
     * sets the pieces Y position
     * 
     * @param y the position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * checks if the piece is white or not
     * 
     * @return whether or not the piece is white
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * sets the color of a piece
     * 
     * @param isWhite the color oyu want to set the piece to
     */
    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /**
     * checks if a position is in bounds
     * 
     * @param x the given X
     * @param y the given Y
     * @return whether or not the given position is in bounds
     */
    public boolean inBounds(int x, int y) {
        return (x >= 0 && x < BoardConstants.SIZE) && (y >= 0 && y < BoardConstants.SIZE);
    }

    /**
     * visually moves a piece to where it should be
     * 
     * @param x board coordinate X
     * @param y board coordinate Y
     */
    public void relocatePiece(int x, int y) {
        relocate(VisualConstants.X_OFFSET + x * VisualConstants.TILE_SIZE,
                VisualConstants.Y_OFFSET + y * VisualConstants.TILE_SIZE);
    }

}
