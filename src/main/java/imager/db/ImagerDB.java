package imager.db;

import java.util.ArrayList;
import java.util.List;

import imager.models.ImagerFile;

public class ImagerDB {
    private List<ImagerFile> imagerFiles;

    public ImagerDB() {
        this.imagerFiles = new ArrayList<ImagerFile>();;
    }

    public void add(ImagerFile imagerFile) {
        this.imagerFiles.add(imagerFile);
    }
}
