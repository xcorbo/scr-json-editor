
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;


public class MainApplication extends Application {
    // Init & Primitive Definitions
    ErrorUnsupportedFile errorUnsupportedFileDialog;
    NormalCases normalCasesDialog;
    String filename;
    FileChooser chooseJSON;
    File selectedJSON;
    Stage lolstage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Setting stage
        Stage mainStage = primaryStage;
        lolstage = mainStage;

        // Set the application icon
        String iconPath = "resources/icon.png"; // Path relative to the resources directory
        Image iconImage = new Image(getClass().getResourceAsStream(iconPath));
        primaryStage.getIcons().add(iconImage);

        // Load the last opened directory from preferences
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        String lastOpenedDir = prefs.get("lastOpenedDir", System.getProperty("user.home"));

        // Check if the lastOpenedDir is a valid directory
        File lastOpenedDirFile = new File(lastOpenedDir);
        if (!lastOpenedDirFile.exists() || !lastOpenedDirFile.isDirectory()) {
            // The directory does not exist or is not a directory, set a default directory
            lastOpenedDir = System.getProperty("user.home"); // You can choose any default directory here
        }

        // Open the file chooser window directly...
        chooseJSON = new FileChooser();
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON Files", "*.json");
        chooseJSON.getExtensionFilters().addAll(jsonFilter);
        chooseJSON.setInitialDirectory(new File(lastOpenedDir));
        chooseJSON.setTitle("Choose SC: Remastered *.JSON File");
        selectedJSON = chooseJSON.showOpenDialog(null);

        // Choose the proper window to go to
        if (selectedJSON == null) {
            Platform.exit();
        } else {
            filename = selectedJSON.getName();
            int validation = validateFileName(filename);

            if (validation == 0){
                specialCases(filename, validation, mainStage);
            }
            if (validation == 1){
                uiCases(filename, validation, mainStage);
            }
            if (validation == 2){
                filename = selectedJSON.getAbsolutePath();
                normalCases(selectedJSON, mainStage);
            }
            if (validation == 3){
                superWrongCase(filename, validation, mainStage);
            }
        }
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
    private void specialCases(String filename, int validation, Stage mainStage) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-unsupported-file.fxml"));

        // Get root parent and load controller
        Parent errorDialogRoot = loader.load();
        errorUnsupportedFileDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        errorUnsupportedFileDialog.error(filename, validation);

        // Create a new stage for the error dialog scene
        Scene errorDialogScene = new Scene(errorDialogRoot);

        // Set the scene and show the new stage (error dialog)
        mainStage.setScene(errorDialogScene);
        mainStage.setTitle("SC: Remastered JSON Editor");
        mainStage.show();
    }

    private void uiCases(String filename, int validation, Stage mainStage) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-unsupported-file.fxml"));

        // Get root parent and load controller
        Parent errorDialogRoot = loader.load();
        errorUnsupportedFileDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        errorUnsupportedFileDialog.error(filename, validation);

        // Create a new stage for the error dialog scene
        Scene errorDialogScene = new Scene(errorDialogRoot);

        // Set the scene and show the new stage (error dialog)
        mainStage.setScene(errorDialogScene);
        mainStage.setTitle("SC: Remastered JSON Editor");
        mainStage.show();
    }

    private void normalCases(File selectedJSON, Stage mainStage) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("normal-cases.fxml"));

        // Get root parent and load controller
        Parent normalCasesRoot = loader.load();
        normalCasesDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        normalCasesDialog.getJSON(selectedJSON);

        // Create a new stage for the error dialog scene
        Scene normalCasesScene = new Scene(normalCasesRoot);

        // Set the scene and show the new stage (error dialog)
        mainStage.setScene(normalCasesScene);
        mainStage.setTitle("SC: Remastered JSON Editor -- " + filename);
        mainStage.show();
    }
    private void superWrongCase(String filename, int validation, Stage mainStage) throws IOException {
        // Load whatever FXML we have to
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-unsupported-file.fxml"));

        // Get root parent and load controller
        Parent errorDialogRoot = loader.load();
        errorUnsupportedFileDialog = loader.getController();

        // Pass arguments to error function in the error dialog
        errorUnsupportedFileDialog.error(filename, validation);

        // Create a new stage for the error  dialog scene
        Scene errorDialogScene = new Scene(errorDialogRoot);

        // Set the scene and show the new stage (error dialog)
        mainStage.setScene(errorDialogScene);
        mainStage.setTitle("SC: Remastered JSON Editor");
        mainStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Save the last opened directory to preferences before the application closes
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        if (selectedJSON != null) {
            prefs.put("lastOpenedDir", selectedJSON.getParent());
        }

        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}




