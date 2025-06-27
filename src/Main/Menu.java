package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class Menu extends Scene {
    public VBox root;
    private static final double MIN_WIDTH = 300;
    private static final double MIN_HEIGHT = 400;

    public Menu(App app) {
        super(createMenu(app), getOptimalWidth(), getOptimalHeight());

        // Make the scene responsive to window resizing
        this.widthProperty().addListener(_ -> {
            updateLayout();
        });

        this.heightProperty().addListener(_ -> {
            updateLayout();
        });
    }

    private static double getOptimalWidth() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        return Math.max(MIN_WIDTH, Math.min(800, screenWidth * 0.6));
    }

    private static double getOptimalHeight() {
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        return Math.max(MIN_HEIGHT, Math.min(600, screenHeight * 0.7));
    }

    private void updateLayout() {
        if (root != null) {
            double currentWidth = getWidth();
            double currentHeight = getHeight();

            // Update padding based on screen size
            double paddingPercent = Math.max(0.03, Math.min(0.08, currentWidth * 0.00005));
            double padding = currentWidth * paddingPercent;
            root.setPadding(new Insets(padding));

            // Update spacing based on screen size
            double spacing = Math.max(10, Math.min(30, currentHeight * 0.03));
            root.setSpacing(spacing);

            // Update font sizes
            updateFontSizes(currentWidth, currentHeight);
        }
    }

    private void updateFontSizes(double width, double height) {
        double baseSize = Math.min(width, height);

        root.getChildren().forEach(node -> {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (label.getText().equals("Chess Game Options")) {
                    // Title font size
                    double titleSize = Math.max(18, Math.min(40, baseSize * 0.08));
                    label.setFont(Font.font("Arial", FontWeight.BOLD, titleSize));
                } else if (label.getText().equals("Configure AI Rules")) {
                    // Subtitle font size
                    double subtitleSize = Math.max(12, Math.min(18, baseSize * 0.04));
                    label.setFont(Font.font("Arial", FontWeight.NORMAL, subtitleSize));
                }
            } else if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals("Start Game")) {
                    // Start button font size
                    double buttonFontSize = Math.max(14, Math.min(20, baseSize * 0.045));
                    button.setFont(Font.font("Arial", FontWeight.BOLD, buttonFontSize));

                    // Update button size
                    double buttonWidth = Math.max(120, Math.min(200, width * 0.25));
                    double buttonHeight = Math.max(35, Math.min(50, height * 0.08));
                    button.setPrefSize(buttonWidth, buttonHeight);
                }
            }
        });
    }

    private static VBox createMenu(App app) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setFillWidth(true);
        root.setStyle("-fx-background-color: #34495e;");

        // Title
        Label titleLabel = new Label("Chess Game Options");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Subtitle
        Label subtitleLabel = new Label("Configure AI Rules");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitleLabel.setTextFill(Color.LIGHTGRAY);
        subtitleLabel.setMaxWidth(Double.MAX_VALUE);
        subtitleLabel.setAlignment(Pos.CENTER);

        // Options container with scroll capability
        VBox optionsContainer = new VBox();
        optionsContainer.setAlignment(Pos.CENTER);
        optionsContainer.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 15;");

        // Create a grid-like layout using HBox rows
        for (int i = 0; i < Constants.aiRules.length; i += 2) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER);
            row.setSpacing(15);
            row.setPadding(new Insets(5));

            // First button in the row
            OptionButton optionButton1 = new OptionButton(i, Constants.aiRulesNames[i]);
            optionButton1.setSelected(Constants.aiRules[i]);
            optionButton1.setPrefWidth(200); // Fixed width instead of max width
            optionButton1.setMaxWidth(200);
            row.getChildren().add(optionButton1);

            // Second button in the row (if it exists)
            if (i + 1 < Constants.aiRules.length) {
                OptionButton optionButton2 = new OptionButton(i + 1, Constants.aiRulesNames[i + 1]);
                optionButton2.setSelected(Constants.aiRules[i + 1]);
                optionButton2.setPrefWidth(200); // Fixed width instead of max width
                optionButton2.setMaxWidth(200);
                row.getChildren().add(optionButton2);
            }

            optionsContainer.getChildren().add(row);
        }

        // Wrap options in a scroll pane for overflow handling
        ScrollPane scrollPane = new ScrollPane(optionsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setPrefViewportHeight(Region.USE_COMPUTED_SIZE);
        scrollPane.setMaxHeight(Double.MAX_VALUE);

        // Make scroll pane grow with available space
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Start button
        Button startButton = new Button("Start Game");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        startButton.setPrefSize(180, 40);
        startButton.setMaxWidth(250); // Limited max width instead of full width
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

        // Add spacing regions for better distribution
        Region topSpacer = new Region();
        Region bottomSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.SOMETIMES);
        VBox.setVgrow(bottomSpacer, Priority.SOMETIMES);

        root.getChildren().addAll(
                topSpacer,
                titleLabel,
                subtitleLabel,
                scrollPane,
                startButton,
                bottomSpacer);

        return root;
    }
}