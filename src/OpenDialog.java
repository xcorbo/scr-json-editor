import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class OpenDialog extends MainApplication {

    @FXML
    private Button OpenDialogOpenButton, OpenDialogCloseButton;
    private JSONObject JSON;

    @FXML
    protected void onOpenDialogOpenButtonClick(){
        try {
            // Load the test-dialog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test-dialog.fxml"));
            Parent testDialogRoot = loader.load();
            TestDialog testString = loader.getController();

            // Create a new stage for the test-dialog scene
            Stage testDialogStage = new Stage();
            Scene testDialogScene = new Scene(testDialogRoot);

            // Set the scene and show the new stage (test-dialog)
            testDialogStage.setScene(testDialogScene);
            testDialogStage.show();

            // Close old shit
            Stage currentStage = (Stage) OpenDialogOpenButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
