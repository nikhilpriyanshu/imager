package filemodels;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Collection;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImgFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String processedFileList = "/home/npriyanshu/Desktop/processed_class.txt";
    File file;
    Metadata metadata;

    public ImgFile(File file) {
        this.file = file;
        try {
            this.metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException | IOException e) {
            writeLogFiles(processedFileList, this.file.getName());
        }
    }

    public Iterable<Directory> getDirectories() {
        return metadata.getDirectories();
    }

    public File getFile() {
        return file;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public boolean equals(ImgFile file) {
        if (this.metadata.getDirectoryCount() != file.getMetadata().getDirectoryCount()) {
            return false;
        }
        Iterable<Directory> fileDirectories = file.getMetadata().getDirectories();
        for (Directory directory : this.metadata.getDirectories()) {
            boolean dirFound = false;
            for (Directory fileDirectory : fileDirectories) {
                if (directory.getName().equals(fileDirectory.getName())) {
                    dirFound = true;
                    if (directory.getTagCount() != fileDirectory.getTagCount()) {
                        return false;
                    }
                    Collection<Tag> tags = directory.getTags();
                    Collection<Tag> fileTags = fileDirectory.getTags();
                    for (Tag tag : tags) {
                        boolean tagFound = false;
                        for (Tag fileTag : fileTags) {
                            if (tag.getTagName().equals(fileTag.getTagName())) {
                                tagFound = true;
                                if (!tag.getDescription().equals(fileTag.getDescription())) {
                                    return false;
                                }
                            }
                        }
                        if(!tagFound) {
                            return false;
                        }
                    }
                }
            }
            if (!dirFound) {
                return false;
            }
        }
        return true;
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
