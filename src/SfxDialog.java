import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static javafx.application.Platform.exit;

public class SfxDialog {

    // Tree Setup
    @FXML
    private TreeView<String> treeViewFX_sfx;
    private TreeItem<String> rootItem;

    // Content Fields
    @FXML
    public TextField idTextField_sfx, priorityTextField_sfx, lengthAdjustTextField_sfx, minVolumeTextField_sfx;
    public CheckBox preloadCheckBox_sfx, unitSpeechCheckBox_sfx, oneAtATimeCheckBox_sfx, neverPreemptCheckBox_sfx;

    // App Settings Buttons
    @FXML
    public RadioButton idRadioButton_sfx, filePathRadioButton_sfx;

    // Bottom Nav Buttons
    @FXML
    public TextField searchTextField_sfx;
    @FXML
    public Button searchButton_sfx, openButton_sfx, saveButton_sfx, closeButton_sfx;

    // The variables above all
    public File toWriteJSON;
    int idOrNot = 0; // Setting to toggle tree view by id = 0, filePath = 1

    // JSONObject here because, sure, why not.
    File globalJSON;


    public void getJSON_sfx(File selectedJSON){
        // Initial JSON TO BE UNCHANGED EVER
        globalJSON = selectedJSON;

        // JSON that will be allowed to write stuff into +  will be outputed later
        toWriteJSON = selectedJSON;

        // Check if toggle is set, if not, set default to display tree by id
        if(filePathRadioButton_sfx.isSelected() == false && idRadioButton_sfx.isSelected() == false ){
            idRadioButton_sfx.setSelected(true);
        }


        // Get the JSON from FILE and start the tree root + init the population functions
        // Checking if it has contents...
        if (selectedJSON != null) {

            try (FileReader reader = new FileReader(selectedJSON)) {

                // Create the root called sfx. This is hardcoded, it shouldn't be.
                rootItem = new TreeItem<>("sfx");
                treeViewFX_sfx.setRoot(rootItem);

                // Parse the JSON data into a JsonObject
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(reader);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Populate the sfx root item
                populateSfx(rootItem, jsonObject);

                // Show the root or not, in this case, yes please.
                treeViewFX_sfx.setShowRoot(true);

            } catch (IOException | JsonParseException e) {
                e.printStackTrace();
            }
        }
    }

    //
    // POPULATION FUNCTIONS START HERE
    //

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

                // Create the items
                TreeItem<String> commentsItem = new TreeItem<>("comments");

                // Call populate Comments to fill the items
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
                populateOriginal(scrItem, scrObject);

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

                            String entryLabel;

                            // Customize this to display entry properties as needed
                            if (idOrNot == 1){
                                entryLabel = entryObject.get("filePath").getAsString();
                            } else {
                                entryLabel = entryObject.get("ID").getAsString();
                            }

                            TreeItem<String> entryItem = new TreeItem<>(entryLabel);
                            categoryItem.getChildren().add(entryItem);
                        }
                    }
                }
            }
        }
    }

    //
    // HANDLING OF CLICKS FUNCTIONS START HERE
    //

    @FXML
    protected void handleTreeItemClicks_sfx(){

        // Select each ListView object and set them to a new JSON Object for it to be previewed
        String valuesStrings = treeViewFX_sfx.getSelectionModel().getSelectedItem().getValue();
        System.out.println("LMAO DEBUG:" + valuesStrings);
        JSONObject JSONObjectStringsValues = new JSONObject(valuesStrings);

        // Get all the values, strings first
        idTextField_sfx.setText(JSONObjectStringsValues.getString("ID"));
        priorityTextField_sfx.setText(JSONObjectStringsValues.get("priority").toString());
        lengthAdjustTextField_sfx.setText(JSONObjectStringsValues.get("lengthAdjust").toString());
        minVolumeTextField_sfx.setText(JSONObjectStringsValues.get("minVolume").toString());

        // Get all the values, now checkboxes
        preloadCheckBox_sfx.setSelected(JSONObjectStringsValues.getBoolean("preload"));
        unitSpeechCheckBox_sfx.setSelected(JSONObjectStringsValues.getBoolean("unitSpeech"));
        oneAtATimeCheckBox_sfx.setSelected(JSONObjectStringsValues.getBoolean("oneAtATime"));
        neverPreemptCheckBox_sfx.setSelected(JSONObjectStringsValues.getBoolean("neverPreempt"));

    }

    //
    // SETTINGS FUNCTIONS START HERE
    //

    @FXML
    protected void idToggle_sfx(){
        idRadioButton_sfx.setSelected(true);
        filePathRadioButton_sfx.setSelected(false);
        idOrNot = 0;
        getJSON_sfx(globalJSON);
    }

    @FXML
    protected void filePathToggle_sfx(){
        idRadioButton_sfx.setSelected(false);
        filePathRadioButton_sfx.setSelected(true);
        idOrNot = 1;
        getJSON_sfx(globalJSON);
    }

    //
    // BOTTOM NAVIGATION FUNCTIONS START HERE
    //

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