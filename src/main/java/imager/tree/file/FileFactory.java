package imager.tree.file;

import java.io.File;

public class FileFactory {
    public static IFile getFile(File file) {
        if (file == null) {
            return null;
        }
        if (file.isDirectory()) {
            return new DirFile(file);
        } else {
            return new ImgFile(file);
        }
    }
}
