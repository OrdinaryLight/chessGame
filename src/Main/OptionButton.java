package Main;

import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OptionButton extends ToggleButton {
    private Text text;

    public OptionButton(int num, String text) {
        super(text);
        this.text = new Text(text);

        setMinSize(200, 40);
        setMaxSize(300, 40);
        setPrefSize(250, 40);

        relocate(50, 60 + (50 * num));

        setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        setStyle("-fx-background-color: #ecf0f1; " +
                "-fx-border-color: #bdc3c7; " +
                "-fx-border-width: 2px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px;");

        selectedProperty().addListener((_, _, isSelected) -> {
            if (isSelected) {
                setStyle("-fx-background-color: #3498db; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: #2980b9; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-radius: 8px;");
            } else {
                setStyle("-fx-background-color: #ecf0f1; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: #bdc3c7; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-radius: 8px;");
            }
        });

        toggle(num);
    }

    public void toggle(int num) {
        setOnMousePressed(_ -> {
            System.out.println("pressed " + text);
            Constants.aiRules[num] = !Constants.aiRules[num];
        });
    }
}