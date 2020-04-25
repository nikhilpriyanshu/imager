package handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import filemodels.ImageFile;

public class ImageFileHandler {
    private List<ImageFile> imagefileList = null;
    private int loadingStatus = 0;
    private final int LOADING_STATUS_LENGTH = 50;
    private final int PER_DIR_FILE_NUMBER = 500;
    private File destinationDirectoryPath = null;

    public ImageFileHandler(List<File> fileList, String destinationDirectoryPath) {
        this.imagefileList = new ArrayList<ImageFile>();
        this.destinationDirectoryPath = new File(destinationDirectoryPath);
        ImageFile imageFile;
        for (File file : fileList) {
            if (file.isDirectory()) {
                continue;
            }
            try {
                imageFile = new ImageFile(file);
            } catch (Exception e) {
                continue;
            }
            this.imagefileList.add(imageFile);
            updateStatus();
        }
    }

    private void updateStatus() {
        if (loadingStatus < LOADING_STATUS_LENGTH) {
            System.out.print(".");
        }
        if (loadingStatus == LOADING_STATUS_LENGTH) {
            System.out.println("\n");
        }
        loadingStatus = loadingStatus == LOADING_STATUS_LENGTH ? 0 : loadingStatus + 1;
    }

    public List<ImageFile> getImagefileList() {
        return imagefileList;
    }

    public void sort() {
        imagefileList.sort(new Comparator<ImageFile>() {
            public int compare(ImageFile img1, ImageFile img2) {
                return Long.compare(img1.getDateAsLong(), img2.getDateAsLong());
            }
        });
    }

    public void arrangeInDirectories() {
        if (!destinationDirectoryPath.exists()) {
            destinationDirectoryPath.mkdir();
        }
        for (ImageFile imageFile : imagefileList) {
            try {
                moveFile(imageFile);
            } catch (FileAlreadyExistsException e) {
                System.out.println("Error copying file : " + imageFile.getImageFile().getAbsolutePath());
                moveFileWithRename(imageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void moveFile(ImageFile imageFile) throws Exception {
        String desPath = getDestinationPath(imageFile);
        if (desPath == null) {
            return;
        }
        File destPathFile = new File(desPath);
        if (!destPathFile.exists()) {
            destPathFile.mkdirs();
        }
        desPath = desPath + File.separatorChar + imageFile.getImageFile().getName();
        if (!desPath.contains("null")) {
            Path sourPath = imageFile.getImageFile().toPath();
            Path destPath = new File(desPath).toPath();
            Files.move(sourPath, destPath);
        }
    }

    private void moveFileWithRename(ImageFile imageFile) {
        String desPath = getDestinationPath(imageFile);
        if (desPath == null) {
            return;
        }
        File destPathFile = new File(desPath);
        if (!destPathFile.exists()) {
            destPathFile.mkdirs();
        }
        desPath = desPath + File.separatorChar + imageFile.getImageFile().getName() + "12";
        if (!desPath.contains("null")) {
            Path sourPath = imageFile.getImageFile().toPath();
            Path destPath = new File(desPath).toPath();
            try {
                Files.move(sourPath, destPath);
            } catch (IOException e) {
                System.out.println("Error while moving again for " + imageFile.getImageFile().getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    private String getDestinationPath(ImageFile imageFile) {
        String desPath = null;
        int currentYear = imageFile.getDate().getYear();
        int currentMonth = imageFile.getDate().getMonth();
        int currentDay = imageFile.getDate().getDate();
        System.out.println("fileName : " + imageFile.getImageFile().getName() + " Date : " + imageFile.getDate());
        desPath = destinationDirectoryPath.getAbsolutePath() + File.separatorChar + currentYear + File.separatorChar
                + currentMonth + File.separatorChar + currentDay;
        System.out.println(desPath);
        return desPath != null ? desPath : null;
    }
}
