package imager.models;

import org.json.simple.JSONObject;

public class ImagerFile {
    private String fileName;
    private JSONObject properties;

    public ImagerFile(String fileName, JSONObject properties) {
        super();
        this.fileName = fileName;
        this.properties = properties;
    }

    public String getFileName() {
        return fileName;
    }

    public JSONObject getJSONPropertyObject() {
        return properties;
    }
}
