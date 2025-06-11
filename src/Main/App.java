package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//////////
/// Main class, main connection of visual and logic
/// *Used as such
/// 
public class App extends Application {
    public Board board = new Board();
    private boolean isWhiteTurn = true;
    private Stage stage;
    private Input input = new Input(board, isWhiteTurn, this);
    private Scene gameScene;

    public void start(Stage primaryStage) {
        stage = primaryStage;
        gameScene = new Scene(board.board);
        stage.setTitle("Chess Game");
        stage.setScene(gameScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void restart() {
        // board.reset();
        board = new Board();
        gameScene = new Scene(board.board);
        stage.setTitle("Chess Game");
        stage.setScene(gameScene);
        stage.show();
    }

    public void checkMate() {
        stage.setScene(new CheckMateScene(this));
        stage.show();
    }

}
