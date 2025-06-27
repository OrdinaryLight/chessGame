package Pieces;

import Main.Board;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

//////////
/// Class represents a Piece in chess
/// 
/// 
public abstract class Piece extends StackPane {
    int x; // X coordinate
    int y; // Y coordinate
    boolean isWhite; // color of Piece
    ImageView image; // image associated with Piece
    Board board; // the board
    int isFirstMove;

    public Piece(int x, int y, boolean isWhite, Board board) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.board = board;
        this.isFirstMove = 0;

        image = new ImageView();
        image.setImage(getImage());
        image.setFitWidth(Constants.TILE_SIZE);
        image.setFitHeight(Constants.TILE_SIZE);
        getChildren().add(image);
        relocatePiece(x, y);
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
     * 
     * 
     * @param newX
     * @param newY
     * @return
     */
    public abstract boolean moveCollides(int newX, int newY);

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
        relocatePiece(x, y);
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
        relocatePiece(x, y);

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
        return (x >= 0 && x < Constants.SIZE) && (y >= 0 && y < Constants.SIZE);
    }

    /**
     * visually moves a piece to where it should be
     * 
     * @param x board coordinate X
     * @param y board coordinate Y
     */
    public void relocatePiece(int x, int y) {
        Platform.runLater(() -> {
            relocate(Constants.X_OFFSET + x * Constants.TILE_SIZE,
                    Constants.Y_OFFSET + y * Constants.TILE_SIZE);

        });
    }

    public void hasMoved() {
        isFirstMove++;
    }

    public void hasntMoved() {
        isFirstMove--;
    }

}