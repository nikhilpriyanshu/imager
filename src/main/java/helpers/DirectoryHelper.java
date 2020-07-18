package helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DirectoryHelper {
    private static List<String> filteredFileExtensions = new ArrayList<String>(
            Arrays.asList(".ini", ".db", ".pdf", ".docx", ".java", ".xlsx", ".zip", ".nomedia", ".exe"));

    public static List<File> getFileList(File directoryPath) {
        if (!directoryPath.exists()) {
            return Collections.emptyList();
        }
        List<File> files = new ArrayList<File>();
        File[] fileArray = directoryPath.listFiles();
        for (File file : fileArray) {
            if (file.isDirectory()) {
                files.addAll(getFileList(file));
            } else {
                String fileName = file.getName();
                String extension = null;
                if(fileName.contains(".")) {
                    extension = fileName.substring(fileName.lastIndexOf('.'));
                }
                if (extension != null && !filteredFileExtensions.contains(extension)) {
                    files.add(file);
                }
            }
        }
        return files;
    }
}
