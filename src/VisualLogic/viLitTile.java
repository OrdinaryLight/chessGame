package VisualLogic;

import Constants.VisualConstants;
import javafx.scene.shape.Rectangle;

public class viLitTile extends Rectangle {

    public viLitTile(int x, int y) {
        setWidth(VisualConstants.TILE_SIZE);
        setHeight(VisualConstants.TILE_SIZE);

        relocate(VisualConstants.X_OFFSET + x * VisualConstants.TILE_SIZE,
                VisualConstants.Y_OFFSET + y * VisualConstants.TILE_SIZE);

        // setFill(VisualConstants.LIT1);
        setFill(VisualConstants.LIT1);
        setOpacity(VisualConstants.LIT_OPACITY);
        setVisible(false);
    }

    public void activate(boolean visible) {
        setVisible(visible);
    }
}
