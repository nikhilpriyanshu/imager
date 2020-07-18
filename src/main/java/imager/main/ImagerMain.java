package imager.main;

import java.io.File;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import imager.db.ImagerDB;
import imager.handlers.FileBatchHandler;
import imager.helpers.DirectoryHelper;
import imager.helpers.PropertyHelper;
import imager.models.ImagerFile;

public class ImagerMain {
    private static String directoryPath = "/home/npriyanshu/Images(DoNotChange)/";
    private static ImagerDB imagerDB = new ImagerDB();
    private static final int BATCH_SIZE = 1;

    public static void main(String args[]) {
        //Fetch list of image files from the directoryPath
        File dirFile = new File(directoryPath);
        List<File> fileList = DirectoryHelper.getFileList(dirFile);

        //Read file properties in JSON object and add files with properties to imagerDB
        FileBatchHandler fileBatchHandler = new FileBatchHandler(fileList, BATCH_SIZE);
        while (fileBatchHandler.isNextBatchAvailable()) {
            List<String> files = fileBatchHandler.getNextFileNameBatch();
            JSONArray jsonArray = PropertyHelper.readFileProperties(files);
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                ImagerFile imagerFile = new ImagerFile(jsonObject.get("Image").toString(), jsonObject);
                imagerDB.add(imagerFile);
            }
            System.out.println(fileBatchHandler.getBatchMarker() + "/" + fileList.size());
        }

        //image processing
    }
}