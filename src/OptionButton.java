import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class OptionButton extends ToggleButton {
    private Text text;

    public OptionButton(int num, String text) {
        super(text);
        this.text = new Text(text);
        setMinSize(60, 30);
        setMaxSize(10000, 30);
        relocate(30, 40 * num);
        toggle(num);
    }

    public void toggle(int num) {
        setOnMousePressed(_ -> {
            System.out.println("pressed " + text);
            Constants.aiRules[num] = !Constants.aiRules[num];
        });

    }

}
