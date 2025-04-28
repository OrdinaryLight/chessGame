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

    public void start(Stage primaryStage) {
        stage = primaryStage;

        /*
         * Scene start = new Scene(makeStartScreen());
         * stage.setTitle("Start menu");
         * stage.setScene(start);
         * stage.show();
         */

        playGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void playGame() {
        Scene scene = new Scene(board);
        stage.setTitle("Chess Game");
        stage.setScene(scene);
        stage.show();

    }
    /*
     * public Pane makeStartScreen() {
     * Pane root = new Pane();
     * root.setPrefSize(Constants.TILE_SIZE * Constants.SIZE,
     * Constants.TILE_SIZE * Constants.SIZE);
     * root.setBackground(new Background(new BackgroundFill(Constants.BACKGROUND1,
     * null, null)));
     * Group buttonsGroup = new Group();
     * ToggleButton whiteAiButton = new OptionButton(Constants.DO_WHITE_AI_IDX,
     * "white player ai");
     * ToggleButton blackAiButton = new OptionButton(Constants.DO_BLACK_AI_IDX,
     * "black player ai");
     * ToggleButton doEnPassants = new OptionButton(Constants.DO_ENPASSANTS_IDX,
     * "force ai's to do enpassant");
     * ToggleButton doCheckMultiplier = new
     * OptionButton(Constants.DO_CHECK_MULTIPLIER_IDX,
     * "checks multiply ai's position score");
     * ToggleButton doCenterPawnsEval = new
     * OptionButton(Constants.DO_CENTER_PAWNS_IDX,
     * "pawns in the center add to ai's position score");
     * 
     * Button start = new Button("Start");
     * start.setOnMousePressed(_ -> {
     * playGame();
     * 
     * });
     * start.setMinSize(100, 70);
     * start.relocate((Constants.TILE_SIZE * Constants.SIZE - 100) / 2,
     * (Constants.TILE_SIZE * Constants.SIZE - 70) / 2);
     * 
     * buttonsGroup.getChildren().addAll(whiteAiButton, blackAiButton, doEnPassants,
     * doCheckMultiplier,
     * doCenterPawnsEval);
     * root.getChildren().addAll(buttonsGroup, start);
     * 
     * return root;
     * }
     * 
     */

}
