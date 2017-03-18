package be.ac.umons.sgl.lazer.g06;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Files {
	/*
	 * Get string content or null if an error occured
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
	
	public static boolean putContent(String path, String content) {
		return putContent(path, content, false);
	}
	
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
	
	public static boolean exists(String path) {
		FileHandle fh = Gdx.files.local(path);
		
		if(fh == null) {
			Gdx.app.debug("Files.exists", "Got null pointer for path "+path);
			return false;
		}
		
		return fh.exists();
	}
	
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
	
	public static String[] list(String path) {
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
		String fileNames[] = new String[files.length];
		
		for(int i = 0; i < files.length; ++i) {
			fileNames[i] = files[i].name();
		}
		
		return fileNames;
	}
	
}
