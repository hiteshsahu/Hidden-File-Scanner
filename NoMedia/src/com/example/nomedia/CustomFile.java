package com.example.nomedia;

public class CustomFile {

	private String fullPath;
	private String name;
	private String directory;
	private boolean isImageFile;

	/**
	 * @return the isImageFile
	 */
	public boolean isImageFile() {
		return isImageFile;
	}

	/**
	 * @param isImageFile the isImageFile to set
	 */
	public void setImageFile(boolean isImageFile) {
		this.isImageFile = isImageFile;
	}

	public CustomFile(String fileName, String path, String fileParent,
			boolean isImageFile) {
		this.name = fileName;
		this.fullPath = path;
		this.directory = fileParent;
		this.isImageFile = isImageFile;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath
	 *            the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @param directory
	 *            the directory to set
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
