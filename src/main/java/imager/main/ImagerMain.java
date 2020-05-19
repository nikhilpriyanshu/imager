package imager.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;

import filemodels.ImgFile;
import handlers.DirectoryHandler;

public class ImagerMain {
    private static File directoryFile = new File("/home/npriyanshu/Images(DoNotChange)/");
    private static String processedFileList = "/home/npriyanshu/Desktop/processed.txt";
    private static String unProcessedFileList = "/home/npriyanshu/Desktop/unprocessed.txt";
    private static List<ImgFile> imgFiles = new ArrayList<ImgFile>();

    public static void main(String args[]) {
        List<File> files = DirectoryHandler.getFileList(directoryFile);
        ListIterator<File> itr = files.listIterator();
        while (itr.hasNext()) {
            File file = itr.next();
            if (file.isDirectory()) {
                continue;
            }
            if (file.getName().contains("._IMG_") || file.getName().contains("IMG_8887.JPG")
                    || file.getName().contains("VVNS0800.MP4") || file.getName().contains("UFAT4521.MP4")
                    || file.getName().contains(".3gp") || file.getName().contains(".AAE")
                    || (file.getName().contains("VID-") && file.getName().contains("-WA"))
                    || (file.getName().contains("IMG-") && file.getName().contains("-WA"))) {
                continue;
            }
            try {
                ImageMetadataReader.readMetadata(file);
                imgFiles.add(new ImgFile(file));
                writeLogFiles(processedFileList, file.getAbsolutePath());
            } catch (ImageProcessingException | IOException e) {
                writeLogFiles(unProcessedFileList, file.getAbsolutePath());
            }
        }
    }

    public static void writeLogFiles(String fileName, String message) {
        try (RandomAccessFile file = new RandomAccessFile(new File(fileName), "rw")) {
            file.seek(file.length());
            file.writeBytes(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}