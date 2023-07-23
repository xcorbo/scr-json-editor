import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestDialog {
    @FXML
    private Label testDialogTestingLabel;
    public TextArea testDialogTextArea;
    JSONArray stringsJSONArray = new JSONArray();
    public Button testDialogOpenButton;
    public void onTestDialogOpenButtonClick(){

    }
    public void getCase(String lol){
        testDialogTextArea.setText(lol);
//        if (selectedJSON != null) {
//            try {
//                FileInputStream inputJSONStream = new FileInputStream(selectedJSON);
//                DataInputStream JSONDataStream = new DataInputStream(inputJSONStream);
//                String entireJSONAsSingleString = IOUtils.toString(JSONDataStream, "UTF-8");
//                JSONArray JSONElementsArray = new JSONArray(entireJSONAsSingleString);
//                testDialogTestingLabel.setText(String.valueOf(JSONElementsArray));
//
//                // stringsJSONArray is a globally accesible variable in this file
////                stringsJSONArray = JSONElementsArray;
////                populateStrings(stringsJSONArray);
////                valueTextFieldFX.setDisable(false);
//
//                // TODO this is just for debugging delete later
//                // formattingTextFX.setText(String.valueOf(JSONElementsArray));
//
//                inputJSONStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
//    @FXML
//    protected void onTestDialogOpenButtonClick(){
//        FileChooser chooseJSON = new FileChooser();
//        chooseJSON.setTitle("Choose SC: Remastered JSON File");
//        File selectedJSON = chooseJSON.showOpenDialog(testDialogOpenButton.getScene().getWindow());
//        if (selectedJSON != null) {
//            try {
//                String selectedFileName = selectedJSON.getName();
//                testDialogTextArea.setText(selectedFileName);
//
//                FileInputStream inputJSONStream = new FileInputStream(selectedJSON);
//                DataInputStream JSONDataStream = new DataInputStream(inputJSONStream);
//////                String entireJSONAsSingleString = IOUtils.toString(JSONDataStream, "UTF-8");
////////                JSONArray JSONElementsArray = new JSONArray(entireJSONAsSingleString);
//////                JSONObject lolJSON = new JSONObject(entireJSONAsSingleString);
////
////                // stringsJSONArray is a globally accesible variable in this file
////                stringsJSONArray = JSONElementsArray;
//////                populateStrings(stringsJSONArray);
//////                valueTextFieldFX.setDisable(false);
////                JSONObject lollmao = new JSONObject(JSONDataStream);
////                testDialogTextArea.setText(String.valueOf(lollmao));
//
//
//                // TODO this is just for debugging delete later
//                // formattingTextFX.setText(String.valueOf(JSONElementsArray));
//
////                inputJSONStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
