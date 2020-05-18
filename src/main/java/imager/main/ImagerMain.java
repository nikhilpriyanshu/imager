package imager.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.ListIterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import handlers.DirectoryHandler;

public class ImagerMain {
	private static File directoryFile = new File("/home/npriyanshu/Images(DoNotChange)/");
	private static String processedFileList = "/home/npriyanshu/Desktop/processed.txt";
	private static String unProcessedFileList = "/home/npriyanshu/Desktop/unprocessed.txt";

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
			Metadata metadata;
			try {
				metadata = ImageMetadataReader.readMetadata(file);
				for (Directory directory : metadata.getDirectories()) {
					if (directory.getName().equals("File Type")) {
						for (Tag tag : directory.getTags()) {
							if (tag.getTagName().contentEquals("Detected MIME Type")
									&& !tag.getDescription().contentEquals("image/jpeg")
									&& !tag.getDescription().contentEquals("image/png")
									&& !tag.getDescription().contentEquals("image/gif")
									&& !tag.getDescription().contentEquals("audio/aac")
									&& !tag.getDescription().contentEquals("application/zip")
									&& !tag.getDescription().contentEquals("video/mp4")
									&& !tag.getDescription().contentEquals("video/quicktime")) {
								System.out.println(tag);
							}
						}
					}
				}
				writeLogFiles(processedFileList, file.getAbsolutePath());
			} catch (ImageProcessingException | IOException e) {
				System.out.println(file.toPath() + "\n" + e);
				e.printStackTrace();
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