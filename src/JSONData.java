import com.google.gson.annotations.SerializedName;

public class JSONData {

    // Serialize the data value names for later use like value.whatever and stuff
    @SerializedName("Value")
    private String value;
    @SerializedName("id")
    private String id;
    @SerializedName("Key")
    private String key;

    // Getters for values
    public String getValue() {
        return value;
    }
    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    // Setters for values
    public void setValue(String value) {
        this.value = value;
    }
    public void setId(String value) {
        this.id = value;
    }
    public void setKey(String value) {
        this.key = value;
    }
}