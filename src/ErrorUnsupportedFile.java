import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class ErrorUnsupportedFile {
    @FXML
    public Button errorUnsupportedFileOpenButton;
    @FXML
    public Label errorUnsupportedFileLabel;
    public String errorString;
    public MainApplication startNew;

    public void error(String filename, int validation){
        // Screens to show error of validation mismatch
        if (validation == 0){
            errorString = "ERROR: currently the file " + filename + " is not supported, please try a different Starcraft: Remastered *.json or *.ui.json file";
        }
        if (validation == 1){
            errorString = "ERROR: currently the extension .ui.json is not supported, please try a different Starcraft: Remastered *.json or *.ui.json file";
        }
        if (validation == 2){
            errorString = "good";
        }
        if (validation == 3){
            errorString = "ERROR: the file " + filename + " is not supported, please try a different Starcraft: Remastered *.json or *.ui.json file";
        }

        errorUnsupportedFileLabel.setText(errorString);
    }

    @FXML
    private void onErrorUnsupportedFileButtonClick() throws IOException {

    }
}