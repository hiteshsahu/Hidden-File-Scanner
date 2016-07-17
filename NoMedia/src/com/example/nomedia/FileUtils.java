package com.example.nomedia;

import java.io.File;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.webkit.MimeTypeMap;

public class FileUtils {

	Context context;

	public FileUtils(Context context) {
		this.context = context;
	}

	public static String getExtensionFromFilePath(String fullPath) {
		String filenameArray[] = fullPath.split("\\.");
		return filenameArray[filenameArray.length - 1];
	}

	public static String getFileName(String fullPath) {
		return fullPath.substring(fullPath.lastIndexOf(File.separator) + 1);
	}

	public static String getFileParent(String fullPath) {
		return fullPath.substring(0, fullPath.lastIndexOf(File.separator));
	}

	public static String getMimeTypeFromFilePath(String filePath) {
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				getExtensionFromFilePath(filePath));
		return (mimeType == null) ? "*/*" : mimeType;
	}

	public static boolean isImage(String fullPath) {

		File imageFile = new File(fullPath);
		if (imageFile == null || !imageFile.exists()) {
			return false;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imageFile.getPath(), options);
		return options.outWidth != -1 && options.outHeight != -1;
	}

}