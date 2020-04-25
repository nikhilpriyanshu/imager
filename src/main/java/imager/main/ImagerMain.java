package imager.main;

import java.io.File;
import java.util.List;

import handlers.DirectoryHandler;
import handlers.ImageFileHandler;

public class ImagerMain {
    private static String sourceDirPath = "D:\\images\\";
    private static String destinationDirPath = "D:\\Workshop\\";

    public static void main(String args[]) {
        File imageDirFile = new File(sourceDirPath);
        List<File> imageFiles = DirectoryHandler.getFileList(imageDirFile);
        for(File file : imageFiles) {
            System.out.println(file.getAbsolutePath());
        }
//        ImageFileHandler imageFileHandler = new ImageFileHandler(imageFiles, destinationDirPath);
//        imageFileHandler.sort();
//        imageFileHandler.arrangeInDirectories();
    }
}