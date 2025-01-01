package VisualLogic;

import Constants.VisualConstants;
import javafx.scene.shape.Rectangle;

//////////
/// A tile to be lit up when clicking on pieces
/// *Used as such
public class viLitTile extends Rectangle {

    public viLitTile(int x, int y) {
        setWidth(VisualConstants.TILE_SIZE);
        setHeight(VisualConstants.TILE_SIZE);

        relocate(VisualConstants.X_OFFSET + x * VisualConstants.TILE_SIZE,
                VisualConstants.Y_OFFSET + y * VisualConstants.TILE_SIZE);

        setFill(VisualConstants.LIT1);
        setOpacity(VisualConstants.LIT_OPACITY);
        setVisible(false);
    }

    /**
     * makes a tile visible or not visible
     * 
     * @param visible whether you want the tile to be visible or not
     */
    public void activate(boolean visible) {
        setVisible(visible);
    }
}
