package imager.tree.file;

import java.io.File;
import java.util.Map;

public class ImgFile implements IFile{
    private File file;
    private Map<String, Map<String, String>> properties;

    public ImgFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<String, Map<String, String>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Map<String, String>> properties) {
        this.properties = properties;
    }
}
