package imager.tree.file;

import java.io.File;
import java.util.LinkedList;

public class FileNode{
    IFile file;
    LinkedList<IFile> children;

    public FileNode(File file) {
        this.file = FileFactory.getFile(file);
    }

    public IFile getFile() {
        return file;
    }

    public void setFile(IFile file) {
        this.file = file;
    }

    public LinkedList<IFile> getChildren() {
        return children;
    }

    public boolean addChild(IFile file) {
        if (children == null) {
            children = new LinkedList<IFile>();
        }
        if (file instanceof DirFile) {
            children.addFirst(file);
        } else if (file instanceof ImgFile) {
            children.addLast(file);
        }
        return false;
    }
}
