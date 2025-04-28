import javafx.scene.shape.Rectangle;

//////////
/// A tile to be lit up when clicking on pieces
/// *Used as such
public class viLitTile extends Rectangle {

    public viLitTile(int x, int y) {
        setWidth(Constants.TILE_SIZE);
        setHeight(Constants.TILE_SIZE);

        relocate(Constants.X_OFFSET + x * Constants.TILE_SIZE,
                Constants.Y_OFFSET + y * Constants.TILE_SIZE);

        setFill(Constants.LIT1);
        setOpacity(Constants.LIT_OPACITY);
        setVisible(false);
    }

}
