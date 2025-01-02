package VisualLogic;

import Constants.BoardConstants;
import Constants.VisualConstants;
import javafx.scene.shape.Rectangle;

//////////
/// Class is a border for the board (make constants so easily changable, also not black, maybe set stroke too)
/// 
public class viBorder extends Rectangle {

    public viBorder() {
        setWidth(VisualConstants.TILE_SIZE * BoardConstants.SIZE + 30);
        setHeight(VisualConstants.TILE_SIZE * BoardConstants.SIZE + 30);
        relocate(VisualConstants.X_OFFSET - 15, VisualConstants.Y_OFFSET - 15);
        setFill(VisualConstants.BORDER1);
        setArcHeight(30);
        setArcWidth(30);
    }

}
