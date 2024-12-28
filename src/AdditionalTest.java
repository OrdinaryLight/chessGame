/*
 * package VisualLogic;
 * 
 * import javafx.scene.image.Image;
 * import javafx.scene.image.ImageView;
 * import javafx.scene.layout.StackPane;
 * 
 * public class Piece extends StackPane {
 * 
 * private double mouseX, mouseY;
 * private double oldX, oldY;
 * 
 * public Piece(Image image, int x, int y) {
 * ImageView imageView = new ImageView(image);
 * imageView.setFitWidth(100); // Set appropriate size for your tiles
 * imageView.setFitHeight(100); // Set appropriate size for your tiles
 * 
 * getChildren().add(imageView);
 * 
 * move(x, y);
 * 
 * setOnMousePressed(e -> {
 * mouseX = e.getSceneX();
 * mouseY = e.getSceneY();
 * });
 * 
 * setOnMouseDragged(e -> {
 * relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
 * });
 * }
 * 
 * public void move(int x, int y) {
 * oldX = x * 100; // TILE_SIZE in your board
 * oldY = y * 100; // TILE_SIZE in your board
 * relocate(oldX, oldY);
 * }
 * 
 * public void abortMove() {
 * relocate(oldX, oldY);
 * }
 * }
 */

 setOnMousePressed(e -> {
    mouseX = e.getSceneX();
    mouseY = e.getSceneY();
});

setOnMouseDragged(e -> {
    relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
});