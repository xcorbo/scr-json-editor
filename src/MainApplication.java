
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;


public class MainApplication extends Application {
    // Init & Primitive Definitions
    ErrorUnsupportedFile errorUnsupportedFileDialog;
    NormalCases normalCasesDialog;
    String filename;
    FileChooser chooseJSON;
    File selectedJSON;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Set the title for the main window
//        primaryStage.setTitle("SC: Remastered JSON Editor");

        // Open the file chooser window directly...
        chooseJSON = new FileChooser();
        chooseJSON.setTitle("Choose SC: Remastered *.JSON File");
        selectedJSON = chooseJSON.showOpenDialog(null);

        // Choose the proper window to go to
        if (selectedJSON != null) {
            filename = selectedJSON.getName();
            int validation = validateFileName(filename);
            if (validation == 0){
                specialCases(filename, validation);
            }
            if (validation == 1){
                uiCases(filename, validation);
            }
            if (validation == 2){
                filename = selectedJSON.getAbsolutePath();
                normalCases(selectedJSON);
            }
            if (validation == 3){
                superWrongCase(filename, validation);
            }
        }
//        Scene scene = new Scene(new VBox()); // Replace VBox with your main layout
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }
    private int validateFileName(String filename) throws IOException {
        // Get the extension
        String extension = FilenameUtils.getExtension(filename);

        // Validate files by using extension. Special cases, ui cases and normal cases, then everything else
        if (filename.equals("flc.json") || filename.equals("sfx.json") || filename.equals("skin.json") || filename.equals("skins.json")  || filename.equals("badnames.json")){
            return 0;
        } else if (FilenameUtils.getBaseName(filename).endsWith(".ui") && FilenameUtils.getExtension(filename).equals("json")){
            return 1;
        } else if (extension.equals("json")){
            return 2;
        } else {
            return 3;
        }
    }
    private void specialCases(String filename, int validation) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-unsupported-file.fxml"));

        // Get root parent and load controller
        Parent errorDialogRoot = loader.load();
        errorUnsupportedFileDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        errorUnsupportedFileDialog.error(filename, validation);

        // Create a new stage for the error dialog scene
        Stage errorDialogStage = new Stage();
        Scene errorDialogScene = new Scene(errorDialogRoot);

        // Set the scene and show the new stage (error dialog)
        errorDialogStage.setScene(errorDialogScene);
        errorDialogStage.setTitle("SC: Remastered JSON Editor");
        errorDialogStage.show();
    }

    private void uiCases(String filename, int validation) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-unsupported-file.fxml"));

        // Get root parent and load controller
        Parent errorDialogRoot = loader.load();
        errorUnsupportedFileDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        errorUnsupportedFileDialog.error(filename, validation);

        // Create a new stage for the error dialog scene
        Stage errorDialogStage = new Stage();
        Scene errorDialogScene = new Scene(errorDialogRoot);

        // Set the scene and show the new stage (error dialog)
        errorDialogStage.setScene(errorDialogScene);
        errorDialogStage.setTitle("SC: Remastered JSON Editor");
        errorDialogStage.show();
    }

    private void normalCases(File selectedJSON) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("normal-cases.fxml"));

        // Get root parent and load controller
        Parent normalCasesRoot = loader.load();
        normalCasesDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        normalCasesDialog.getJSON(selectedJSON);

        // Create a new stage for the error dialog scene
        Stage normalCasesStage = new Stage();
        Scene normalCasesScene = new Scene(normalCasesRoot);

        // Set the scene and show the new stage (error dialog)
        normalCasesStage.setScene(normalCasesScene);
        normalCasesStage.setTitle("SC: Remastered JSON Editor");
        normalCasesStage.show();
    }
    private void superWrongCase(String filename, int validation) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-unsupported-file.fxml"));

        // Get root parent and load controller
        Parent errorDialogRoot = loader.load();
        errorUnsupportedFileDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        errorUnsupportedFileDialog.error(filename, validation);

        // Create a new stage for the error  dialog scene
        Stage errorDialogStage = new Stage();
        Scene errorDialogScene = new Scene(errorDialogRoot);

        // Set the scene and show the new stage (error dialog)
        errorDialogStage.setScene(errorDialogScene);
        errorDialogStage.setTitle("SC: Remastered JSON Editor");
        errorDialogStage.show();
    }

    // Dude we're not even using this lmao
    public static void main(String[] args) {
        launch();
    }
}




