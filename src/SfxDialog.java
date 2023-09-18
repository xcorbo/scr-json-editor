import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class SfxDialog {
    @FXML
    public Label SFXLabelDebug;

    public void test(String stringTest){
        // Just a Test
        SFXLabelDebug.setText(stringTest);
    }
}