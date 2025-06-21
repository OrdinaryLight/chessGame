package Main;

import Pieces.Piece;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//////////
/// Main class, main connection of visual and logic
/// *Used as such
/// 
public class App extends Application {
    public Board board;
    private boolean isWhiteTurn = true;
    private Stage stage;
    private Scene gameScene;
    public Input input;

    public void start(Stage primaryStage) {
        stage = primaryStage;
        board = new Board(this);
        gameScene = new Scene(board.board);
        input = new Input(board, isWhiteTurn, this);
        stage.setTitle("Chess Game");
        stage.setScene(new Menu(this));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start() {
        stage.setTitle("Chess Game");
        stage.setScene(gameScene);
        stage.show();
    }

    public void restart() {
        board = new Board(this);
        gameScene = new Scene(board.board);
        input = new Input(board, isWhiteTurn, this);
        stage.setTitle("Chess Game");
        stage.setScene(gameScene);
        stage.show();
    }

    public void checkMate() {
        stage.setScene(new CheckMateScene(this));
        stage.show();
    }

    public void addPiece(Piece piece) {
        input.addPiece(piece);
    }

}
