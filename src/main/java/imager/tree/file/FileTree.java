package imager.tree.file;

import java.io.File;

public class FileTree {
    private IFile root;
    
    public FileTree(File root) {
        if (root == null || !root.isDirectory()) {
            return;
        }
        this.root = new DirFile(root);
    }

    public boolean addFile(File file) {
        if (this.root == null) {
            return false;
        }
        
        return false;
    }
}
