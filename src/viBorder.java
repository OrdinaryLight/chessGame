import javafx.scene.shape.Rectangle;

//////////
/// Class is a border for the board (make constants so easily changable, also not black, maybe set stroke too)
/// 
public class viBorder extends Rectangle {

    public viBorder() {
        setWidth(Constants.TILE_SIZE * Constants.SIZE + 30);
        setHeight(Constants.TILE_SIZE * Constants.SIZE + 30);
        relocate(Constants.X_OFFSET - 15, Constants.Y_OFFSET - 15);
        setFill(Constants.BORDER1);
        setArcHeight(30);
        setArcWidth(30);
    }

}
