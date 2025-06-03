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
    private Input input = new Input(board, isWhiteTurn);

    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = new Scene(board.board);
        stage.setTitle("Chess Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
