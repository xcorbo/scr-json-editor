
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.*;
import static javafx.application.Platform.exit;

public class NormalCases {
    JSONArray stringsJSONArray = new JSONArray();
    @FXML
    private TextArea formattingTextFX; // This is where i temp debug shit for now
    @FXML
    private Label stringPreviewFX;
    @FXML
    public Button openButtonFX, closeButtonFX, saveButtonFX;
    @FXML
    private ListView stringsListFX;
    @FXML
    private TextField idTextFieldFX, labelTextFieldFX, valueTextFieldFX, searchTextFieldFX;
    @FXML
    public WebView previewTextWebView;

    // General JSON accesible to everywhere
    public File toWriteJSON;

    public void getJSON(File selectedJSON){
        // Set the JSON to the public File
        toWriteJSON = selectedJSON;

        // Make the WebView Background Black + set temporary text
        // Load Contents into the WebView
        String tempTextPreview = "<html><body bgcolor='#000000'></body></html>";
        WebEngine webEngine = previewTextWebView.getEngine();
        webEngine.loadContent(tempTextPreview);

        // Start program
        if (selectedJSON != null) {
            try {
                // Create a FileReader to read the selected JSON file
                FileInputStream inputStream = new FileInputStream(selectedJSON);
                InputStreamReader fileReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

                // Use Gson to parse the JSON data into a list of JSONData objects
                Gson gson = new Gson();
                List<JSONData> myDataList = gson.fromJson(fileReader, new TypeToken<List<JSONData>>() {}.getType());
                fileReader.close();

                stringsJSONArray = new JSONArray(gson.toJson(myDataList));

                // Populate the strings using the JSONArray (if needed)
                populateStrings(stringsJSONArray, selectedJSON);

                // Enable the valueTextFieldFX (if necessary)
                idTextFieldFX.setDisable(false);
                labelTextFieldFX.setDisable(false);
                valueTextFieldFX.setDisable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onCloseButtonClick(){
        exit();
    }

    @FXML
    protected void onSaveButtonClick(){
        writeUpdatedJSONToFile();
    }

    @FXML
    protected void onSearchButtonClick(){
        //What to Search
        String searchArg = searchTextFieldFX.getText();
        searchFilter(searchArg);
    }

    @FXML
    protected void onOpenButtonClick(){
        //What to Search
        addNewJSONObject();
    }

    public void addNewJSONObject() {
        // Default values for new JSON Object just added
        String newValue = "EDIT YOUR NEW STRING";
        String newId = "SUGGESTED_FORMAT_ID";
        String newKey = "SUGGESTED_KEY_FORMAT";

        // Create a new JSONData object and set its properties
        JSONData newJSONData = new JSONData();

        //  Set the values to store
        newJSONData.setValue(newValue);
        newJSONData.setId(newId);
        newJSONData.setKey(newKey);

        // Convert the JSONData object to a JSON string
        Gson gson = new Gson();
        String newJSONString = gson.toJson(newJSONData);

        // Add the new JSON string to the JSONArray
        stringsJSONArray.put(new JSONObject(newJSONString));

        // Add the new JSON string to the ListView
        stringsListFX.getItems().add(newJSONString);
        writeUpdatedJSONToFile();
    }

    @FXML
    protected void handleItemClicksStringsListFX(){
        // Select each ListView object and set them to a new JSON Object for it to be previewed
        String valuesStrings = stringsListFX.getSelectionModel().getSelectedItem().toString();
        JSONObject JSONObjectStringsValues = new JSONObject(valuesStrings);

        // Get All the values
        valueTextFieldFX.setText(JSONObjectStringsValues.getString("Value"));
        idTextFieldFX.setText(JSONObjectStringsValues.getString("id"));
        labelTextFieldFX.setText(JSONObjectStringsValues.getString("Key"));

        // Assign Value to a string and start parsing it for preview
        String previewString = JSONObjectStringsValues.getString("Value");

        // Call the preview parsing method
        parsePreview(previewString);
    }

    public void populateStrings(JSONArray JSONToParse, File selectedJSON){
        // Populating the ListView with the JSONArray elements.
        // TODO need to parse so it only shows the proper string and not the JSON element do i even wanna do this anymore?
        ArrayList listStringsJSONArray = new ArrayList<String>();
        if (JSONToParse != null){
            // This loops for the whole length of the JSONArray, which is all and it adds each element to a new
            // element on a temporary ArrayList that we just made up there named listStringsJSONArray
            for (int i = 0; i < JSONToParse.length(); i++){
                listStringsJSONArray.add(JSONToParse.get(i).toString());
            }
        }
        // With the ArrayList now populated we can now use that to easily convert it into an observableList
        // We use each element of that observableList to populate our ListView that holds the elements on the left pane or our UI
        for (int j = 0; j < listStringsJSONArray.size(); j++){
            ObservableList obList2 = FXCollections.observableList(listStringsJSONArray);
            stringsListFX.getItems().add(obList2.get(j));
        }
    }

    public  void handleRTSearch(){
    //What to Search
    String searchArg = searchTextFieldFX.getText();
    searchFilter(searchArg);
    }

    public  void searchFilter(String sArg){
        // Delete all Items
        stringsListFX.getItems().clear();

        //What to Search
        String searchArg = sArg;

        // Populating the ListView with the JSONArray elements.
        // TODO need to parse so it only shows the proper string and not the JSON element
        ArrayList listStringsJSONArray = new ArrayList<String>();
        if (stringsJSONArray != null){
            // This loops for the whole length of the JSONArray, which is all and it adds each element to a new
            // element on a temporary ArrayList that we just made up there named listStringsJSONArray
            for (int i = 0; i < stringsJSONArray.length(); i++){
                JSONObject searchJSONObject = new JSONObject(stringsJSONArray.get(i).toString());
                String JSONValueToSearch = searchJSONObject.getString("Value");

                // If contains... then filter
                if(JSONValueToSearch.contains(searchArg)){
                    listStringsJSONArray.add(stringsJSONArray.get(i).toString());
                    valueTextFieldFX.setText(JSONValueToSearch + searchArg);
                }
            }
        }

        // With the ArrayList now populated we can now use that to easily convert it into an observableList
        // We use each element of that observableList to populate our ListView that holds the elements on the left pane or our UI
        for (int j = 0; j < listStringsJSONArray.size(); j++){
            ObservableList obList2 = FXCollections.observableList(listStringsJSONArray);
            stringsListFX.getItems().add(obList2.get(j));
        }
    }

    @FXML
    protected void handleTypingValuePreview(){
        // Assign Value to a string and start parsing it for preview
        String previewString = valueTextFieldFX.getText();
        parsePreview(previewString);

        // Update the selected JSONData object in the JSONArray with the edited value
        int selectedIndex = stringsListFX.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            JSONObject selectedJSONObject = stringsJSONArray.getJSONObject(selectedIndex);
            selectedJSONObject.put("Value", previewString);
            stringsListFX.getItems().set(selectedIndex, selectedJSONObject.toString());
        }
    }

    @FXML
    protected void handleTypingIDPreview(){
        // Assign Value to a string and start parsing it for preview
        String previewString = idTextFieldFX.getText();

        // Update the selected JSONData object in the JSONArray with the edited value
        int selectedIndex = stringsListFX.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            JSONObject selectedJSONObject = stringsJSONArray.getJSONObject(selectedIndex);
            selectedJSONObject.put("id", previewString);
            stringsListFX.getItems().set(selectedIndex, selectedJSONObject.toString());
        }
    }

    @FXML
    protected void handleTypingKeyPreview(){
        // Assign Value to a string and start parsing it for preview
        String previewString = labelTextFieldFX.getText();

        // Update the selected JSONData object in the JSONArray with the edited value
        int selectedIndex = stringsListFX.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            JSONObject selectedJSONObject = stringsJSONArray.getJSONObject(selectedIndex);
            selectedJSONObject.put("Key", previewString);
            stringsListFX.getItems().set(selectedIndex, selectedJSONObject.toString());
        }
    }

    public void writeUpdatedJSONToFile() {
        if (toWriteJSON != null) {
            try {
                // Create a FileWriter to write the updated JSON data to the file
                FileWriter fileWriter = new FileWriter(toWriteJSON);

                // Convert the JSONArray to a formatted JSON string and write it to the file
                String updatedJSONString = stringsJSONArray.toString(2);
                fileWriter.write(updatedJSONString);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parsePreview(String stringToPreview){
        //Clear Preview Text
        stringPreviewFX.setText("");
        // Init HTMLColor Class + call the function to color the string
        ColorHTML HTMLString = new ColorHTML();
        String HTMLStringToPreview = HTMLString.colorPreview(stringToPreview);

        // Font type maybe

        // Load Contents into the WebView
        WebEngine webEngine = previewTextWebView.getEngine();
        webEngine.setUserStyleSheetLocation("data:text/css;charset=utf-8," + HTMLString.loadFontCSS());
        Platform.runLater(() -> webEngine.setUserStyleSheetLocation("data:text/css;charset=utf-8," + HTMLString.loadFontCSS()));
        webEngine.loadContent(HTMLStringToPreview);
        }
    }