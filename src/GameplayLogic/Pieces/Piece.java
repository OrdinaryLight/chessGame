package GameplayLogic.Pieces;

import Constants.BoardConstants;
import Constants.VisualConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public abstract class Piece extends StackPane {
    protected int x;
    protected int y;
    protected boolean isWhite;
    protected ImageView image;

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

    // must be inbounds aswell as a space that could be moved to by that piece
    public abstract boolean isValidMove(int newX, int newY);

    public abstract Image getImage();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean inBounds(int x, int y) {
        return (x >= 0 && x < BoardConstants.SIZE) && (y >= 0 && y < BoardConstants.SIZE);
    }

    public void relocatePiece(int x, int y) {
        relocate(VisualConstants.X_OFFSET + x * VisualConstants.TILE_SIZE,
                VisualConstants.Y_OFFSET + y * VisualConstants.TILE_SIZE);
    }

}
