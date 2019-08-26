package com.shoot.io;

import android.os.ParcelFileDescriptor;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Nanling on 16/6/21.
 */
public class XIoUtils {

	public static void closeSilently(Closeable c) {
		if (c != null) try {
			c.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void closeSilently(ParcelFileDescriptor pfd) {
		if (pfd != null) try {
			pfd.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static int copyStream(InputStream is, OutputStream os) throws IOException {
		final byte[] buffer = new byte[4096];
		int count, position = 0;
		while ((count = is.read(buffer)) != -1) {
			os.write(buffer, 0, count);
			position += count;
		}
		return position;
	}

	public static File createTempFile(File file) throws IOException {
		return File.createTempFile(file.getName(), null, file.getParentFile());
	}

	public static void deleteDirectory(File dir) {
		final File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory())
					deleteDirectory(file);
				file.delete();
			}
		}
	}

	public static byte[] readData(InputStream stream, int size) throws IOException {
		final byte[] data = new byte[size];
		if (stream.read(data) != size)
			throw new EOFException();
		return data;
	}
}
