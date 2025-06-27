package Main;

import javafx.scene.shape.Rectangle;

//////////
/// Rectangle for a piece of the board
/// *Used as such
/// 
public class viChessTile extends Rectangle {

    public viChessTile(boolean isWhite, int x, int y) {
        setWidth(Constants.TILE_SIZE);
        setHeight(Constants.TILE_SIZE);
        relocate(Constants.X_OFFSET + x * Constants.TILE_SIZE,
                Constants.Y_OFFSET + y * Constants.TILE_SIZE);
        setFill(isWhite ? Constants.WHITE1 : Constants.BLACK1);
    }
}
