package imager.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileBatchHandler {
    private List<File> files;
    private int batchMarker;
    private int batchSize;

    public FileBatchHandler(List<File> files, int batchSize) {
        this.files = files;
        this.batchSize = batchSize;
        batchMarker = 0;
    }

    public List<String> getNextFileNameBatch() {
        List<String> fileNames = new ArrayList<String>();
        for (int count = 0; count < this.batchSize && batchMarker < files.size(); count++) {
            fileNames.add(this.files.get(batchMarker).getAbsolutePath());
            batchMarker = batchMarker + 1;
        }
        return fileNames;
    }

    public boolean isNextBatchAvailable() {
        return batchMarker < files.size() ? true : false;
    }

    public int getBatchMarker() {
        return batchMarker;
    }
}
