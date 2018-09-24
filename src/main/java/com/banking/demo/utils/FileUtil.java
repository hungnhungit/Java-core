package com.banking.demo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	public static final int BUFFER_SIZE = 100 * 1024;

	private String parentName = null;
	private File dir = null;

	/**
	 * @param parentName
	 * @param dir
	 */

	public FileUtil(String parentName, File dir) {
		this.parentName = parentName;
		this.dir = dir;
	}

	/**
	 * Delete file
	 * 
	 * @param file_path
	 */

	public static void deleteFile(String file_path) {
		File file = new File(file_path);

		if (file.exists()) {
			file.delete();
		}

	}

	/**
	 * deleteDirectory
	 * 
	 * @param path
	 */

	public static void deleteDirectory(String path) {
		if (path != null) {
			File dir = new File(path);
			if (dir.exists()) {
				File[] files = dir.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						deleteFile(files[i].getPath());
					} else {
						deleteDirectory(files[i].getPath());
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param getExtension
	 * @return fileName
	 */

	public static String getExtension(String fileName) {

		int pos = fileName.lastIndexOf('.');

		if (pos != -1) {
			return fileName.substring(pos);
		}

		return fileName;
	}

	
	/**
	 * Upload File
	 * @param in
	 * @param destFile
	 */
	
	public static void appendFile(InputStream in, File destFile) {
		OutputStream out = null;
		try {
			if (destFile.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(destFile, true), BUFFER_SIZE);
			} else {
				out = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
			}
			in = new BufferedInputStream(in, BUFFER_SIZE);

			int len = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
