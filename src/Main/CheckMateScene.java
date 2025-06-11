package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CheckMateScene extends Scene {

    public CheckMateScene(App app) {
        super(createCheckmateScene(app), 1200, 600);
    }

    public static VBox createCheckmateScene(App app) {
        // Create main container
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: #2c3e50;");

        Label checkmateLabel = new Label("CHECKMATE!");
        checkmateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        checkmateLabel.setTextFill(Color.WHITE);

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        gameOverLabel.setTextFill(Color.LIGHTGRAY);

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        playAgainButton.setPrefSize(150, 50);
        playAgainButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10;");

        playAgainButton.setOnMouseEntered(_ -> {
            playAgainButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-background-radius: 10;");
        });

        playAgainButton.setOnMouseExited(_ -> {
            playAgainButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10;");
        });

        playAgainButton.setOnAction(_ -> app.restart());

        root.getChildren().addAll(checkmateLabel, gameOverLabel, playAgainButton);
        return root;
    }
}