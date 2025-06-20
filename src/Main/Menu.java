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

public class Menu extends Scene {
    public VBox root;

    public Menu(App app) {
        super(createMenu(app), 800, 400);
    }

    private static VBox createMenu(App app) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #34495e;");

        // Title
        Label titleLabel = new Label("Chess Game Options");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);

        // Subtitle
        Label subtitleLabel = new Label("Configure AI Rules");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitleLabel.setTextFill(Color.LIGHTGRAY);

        // Options container
        VBox optionsContainer = new VBox();
        optionsContainer.setAlignment(Pos.CENTER);
        optionsContainer.setSpacing(15);
        optionsContainer.setPadding(new Insets(20));
        optionsContainer.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 15;");

        // Create option buttons using Constants arrays
        for (int i = 0; i < Constants.aiRules.length; i++) {
            OptionButton optionButton = new OptionButton(i, Constants.aiRulesNames[i]);
            optionButton.setSelected(Constants.aiRules[i]); // Set initial state
            optionsContainer.getChildren().add(optionButton);
        }

        Button startButton = new Button("Start Game");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        startButton.setPrefSize(180, 30);
        startButton.setStyle("-fx-background-color: #27ae60; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 10; " +
                "-fx-border-radius: 10;");

        // Hover effects for start button
        startButton.setOnMouseEntered(_ -> {
            startButton.setStyle("-fx-background-color: #229954; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10;");
        });

        startButton.setOnMouseExited(_ -> {
            startButton.setStyle("-fx-background-color: #27ae60; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10;");
        });

        startButton.setOnAction(_ -> app.start());

        root.getChildren().addAll(titleLabel, subtitleLabel, optionsContainer, startButton);
        return root;
    }
}