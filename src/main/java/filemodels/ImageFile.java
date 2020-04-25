package filemodels;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImageFile {
    File imageFile;
    String date;

    public ImageFile(File imageFile) throws ImageProcessingException, IOException{
        this.imageFile = imageFile;
        init();
    }

    private void init() throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
        for (Directory directory : metadata.getDirectories()) {
            if(directory.getName().equals("File")){
                for (Tag tag : directory.getTags()) {
                    if(tag.getTagName().equals("File Modified Date")) {
                        date = tag.getDescription();
                    }
                }
            }
        }
    }

    public File getImageFile() {
        return imageFile;
    }

    public long getDateAsLong() {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("EEE MMM dd HH:mm:ss XXX yyyy");
        try {
            return simpleDateFormate.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public Date getDate() {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("EEE MMM dd HH:mm:ss XXX yyyy");
        try {
            return simpleDateFormate.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public String getDateString() {
        return date;
    }
}
