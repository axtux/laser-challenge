package be.ac.umons.sgl.lazer.g06;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
/**
 * To easily manage files using Gdx.files.local interface
 */
public class Files {
	/**
	 * Get whole file content into a string.
	 * @param path Relative path to application root.
	 * @return String containing whole file content or null if an error occurred.
	 */
	public static String getContent(String path) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.getContent", "Got null pointer for path "+path);
			return null;
		}
		
		if(fh.isDirectory()) {
			Gdx.app.debug("Files.getContent", path+" is a directory");
			return null;
		}
		
		String content;
		try {
			content = fh.readString("UTF-8");
		} catch(GdxRuntimeException e) {
			Gdx.app.debug("Files.getContent", "Error reading "+path+" : "+e.getMessage());
			return null;
		}
		
		return content;
	}
	/**
	 * Call {@link #putContent(String, String, boolean)} with append value set to false
	 * @param path Relative path to application root.
	 * @param content Whole file content as a string.
	 * @return True on success, false on error.
	 */
	public static boolean putContent(String path, String content) {
		return putContent(path, content, false);
	}
	/**
	 * Write string to file.
	 * @param path Relative path to application root.
	 * @param content Whole file content as a string.
	 * @param append Whether the content has to be added at the end of the file.
	 * If not, any existing file will be overwritten.
	 * @return True on success, false on error.
	 */
	public static boolean putContent(String path, String content, boolean append) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.putContent", "Got null pointer for path "+path);
			return false;
		}
		
		if(fh.isDirectory()) {
			Gdx.app.debug("Files.putContent", path+" is a directory");
			return false;
		}
		
		try {
			fh.writeString(content, append, "UTF-8");
		} catch(GdxRuntimeException e) {
			Gdx.app.debug("Files.putContent", "Error writing "+path+" : "+e.getMessage());
			return false;
		}
		
		return true;
	}
	/**
	 * Check that a file exists.
	 * @param path Relative path to application root.
	 * @return True if file exists, false otherwise.
	 */
	public static boolean exists(String path) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.exists", "Got null pointer for path "+path);
			return false;
		}
		
		return fh.exists();
	}
	/**
	 * Check that path is real file.
	 * @param path Relative path to application root.
	 * @return True if path is a file, false otherwise (path could be directory or not exist).
	 */
	public static boolean isFile(String path) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.isFile", "Got null pointer for path "+path);
			return false;
		}
		
		if(fh.isDirectory()) {
			Gdx.app.debug("Files.isFile", path+" is a directory");
			return false;
		}
		
		return fh.exists();
	}
	/**
	 * Check that path is a directory.
	 * @param path Relative path to application root.
	 * @return True if path is a directory. False otherwise (path could be a file or not exist).
	 */
	public static boolean isDir(String path) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.isFile", "Got null pointer for path "+path);
			return false;
		}
		
		if(!fh.isDirectory()) {
			Gdx.app.debug("Files.isFile", path+" is a directory");
			return false;
		}
		
		return fh.exists();
	}
	/**
	 * List directory.
	 * @param path Relative path to application root.
	 * @return Array of string filenames (relative to their own directory).
	 */
	public static Array<String> list(String path) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.list", "Got null pointer for path "+path);
			return null;
		}
		
		if(!fh.isDirectory()) {
			Gdx.app.debug("Files.list", path+" is not a directory");
			return null;
		}
		
		FileHandle files[] = fh.list();
		Array<String> fileNames = new Array<String>(files.length);
		
		for(FileHandle file : files) {
			fileNames.add(file.name());
		}
		
		return fileNames;
	}
	/**
	 * Just as {@link #list(String)} but make sure entries are real files (not directory).
	 * @param path Relative path to application root.
	 * @return Array of string filenames (relative to their own directory).
	 */
	public static Array<String> listFiles(String path) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.listFiles", "Got null pointer for path "+path);
			return null;
		}
		
		if(!fh.isDirectory()) {
			Gdx.app.debug("Files.listFiles", path+" is not a directory");
			return null;
		}
		
		FileHandle files[] = fh.list();
		Array<String> fileNames = new Array<String>(files.length);
		
		for(FileHandle file : files) {
			if(file.isDirectory()) {
				continue;
			}
			
			fileNames.add(file.name());
		}
		
		return fileNames;
	}
}
