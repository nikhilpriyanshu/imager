package imager.tree.file;

import java.io.File;

public class DirFile implements IFile{
    private File file;
    
    public DirFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
