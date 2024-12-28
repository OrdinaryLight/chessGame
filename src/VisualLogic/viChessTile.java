package VisualLogic;

import Constants.VisualConstants;
import javafx.scene.shape.Rectangle;

public class viChessTile extends Rectangle {

    public viChessTile(boolean isWhite, int x, int y) {
        setWidth(VisualConstants.TILE_SIZE);
        setHeight(VisualConstants.TILE_SIZE);

        relocate(VisualConstants.X_OFFSET + x * VisualConstants.TILE_SIZE,
                VisualConstants.Y_OFFSET + y * VisualConstants.TILE_SIZE);

        setFill(isWhite ? VisualConstants.WHITE1 : VisualConstants.BLACK1);
    }
}
