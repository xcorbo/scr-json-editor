import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Platform.exit;




public class SfxDialog {


    @FXML
    public Label SFXLabelDebug;

    @FXML
    private TreeView<String> treeViewFX_sfx;
    private TreeItem<String> rootItem;


    // Bottom Nav buttons
    @FXML
    public TextField searchTextField_sfx;
    @FXML
    public Button searchButton_sfx, openButton_sfx, saveButton_sfx, closeButton_sfx;

    // Top vars
    public File toWriteJSON;



    public void getJSON_sfx(File selectedJSON){
        // Set the JSON to the public File
        toWriteJSON = selectedJSON;

        // Start program
        if (selectedJSON != null) {
            try (FileReader reader = new FileReader(selectedJSON)) {
                // Create the root item
                rootItem = new TreeItem<>("sfx");
                treeViewFX_sfx.setRoot(rootItem);

                // Parse the JSON data into a JsonObject
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(reader);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                populateSfx(rootItem, jsonObject);

                treeViewFX_sfx.setShowRoot(true);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
                // Handle JSON parsing error
            }
        }
    }


    public void populateSfx(TreeItem<String> rootItem, JsonObject jsonObject) {
        // Check if the JSON object contains "sfx" property
        if (jsonObject.has("sfx")) {
            JsonObject sfxObject = jsonObject.getAsJsonObject("sfx");

            // Create a TreeItem for "comments" and populate it
            if (sfxObject.has("comments")) {
                JsonArray commentsArray = sfxObject.getAsJsonArray("comments");
                List<String> commentsList = new ArrayList<>();

                for (int i = 0; i < commentsArray.size(); i++) {
                    commentsList.add(commentsArray.get(i).getAsString());
                }

                TreeItem<String> commentsItem = new TreeItem<>("comments");
                populateComments(commentsItem, commentsList);
                rootItem.getChildren().add(commentsItem);
            }

            // Create a TreeItem for "original" and populate it
            if (sfxObject.has("original")) {
                JsonObject originalObject = sfxObject.getAsJsonObject("original");

                TreeItem<String> originalItem = new TreeItem<>("original");
                // You can further populate the "original" section if needed
                populateOriginal(originalItem, originalObject);

                rootItem.getChildren().add(originalItem);
            }

            // Create a TreeItem for "scr" and populate it
            if (sfxObject.has("scr")) {
                JsonObject scrObject = sfxObject.getAsJsonObject("scr");

                TreeItem<String> scrItem = new TreeItem<>("scr");
                // You can further populate the "scr" section if needed

                rootItem.getChildren().add(scrItem);
            }
        }
    }

    private void populateComments(TreeItem<String> parent, List<String> comments) {
        if (comments != null) {
            TreeItem<String> commentsItem = new TreeItem<>("comments");
            parent.getChildren().add(commentsItem);

            for (String comment : comments) {
                TreeItem<String> commentItem = new TreeItem<>(comment);
                commentsItem.getChildren().add(commentItem);
            }
        }
    }

    public void populateOriginal(TreeItem<String> originalItem, JsonObject originalObject) {
        // Check if the "original" section contains any data
        if (originalObject != null) {
            for (String key : originalObject.keySet()) {
                TreeItem<String> categoryItem = new TreeItem<>(key);
                originalItem.getChildren().add(categoryItem);

                JsonObject categoryObject = originalObject.getAsJsonObject(key);

                if (categoryObject.has("entries")) {
                    JsonArray entriesArray = categoryObject.getAsJsonArray("entries");

                    for (JsonElement entryElement : entriesArray) {
                        if (entryElement.isJsonObject()) {
                            JsonObject entryObject = entryElement.getAsJsonObject();

                            // Customize this to display entry properties as needed
                            String entryLabel = entryObject.get("ID").getAsString();
                            TreeItem<String> entryItem = new TreeItem<>(entryLabel);
                            categoryItem.getChildren().add(entryItem);
                        }
                    }
                }
            }
        }
    }


    // Bottom Navigation Functions
    @FXML
    protected void onCloseButton_sfxClick(){
        exit();
    }
    @FXML
    protected void onOpenButton_sfxClick() {
        TreeItem<String> child1 = new TreeItem<>("Child 1");
        TreeItem<String> child2 = new TreeItem<>("Child 2");

        rootItem.getChildren().addAll(child1, child2);
    }
    @FXML
    protected void onSaveButton_sfxClick(){
        // TODO Save Action Function
    }

    @FXML
    protected void handleRTSearch_sfx(){
        //TODO RT
    }
    @FXML
    protected void onSearchButton_sfxClick(){
        //TODO RT
    }
}