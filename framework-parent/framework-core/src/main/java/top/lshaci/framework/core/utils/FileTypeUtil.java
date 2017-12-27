package top.lshaci.framework.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import top.lshaci.framework.core.enums.FileType;

/**
 * File type util
 * 
 * @author lshaci
 * @version 0.0.1
 */
@Slf4j
public abstract class FileTypeUtil {

	/**
	 * Get the file type{@FileType} with file
	 * 
	 * @param file the file
	 * @return the file type
	 * 
	 * @throws FileNotFoundException  if the file not found throw this exception
	 * @throws IOException if read the file has exception throw this
	 */
	public static FileType getType(File file) throws FileNotFoundException, IOException {
		Objects.requireNonNull(file, "The file must not be null");

		return getType(new FileInputStream(file));
	}

	/**
	 * Get the file type {@FileType} with input stream
	 * 
	 * @param is the input stream
	 * @return the file type
	 * @throws IOException if read the file has exception throw this
	 */
	public static FileType getType(InputStream is) throws IOException {
		Objects.requireNonNull(is, "The input stream must not be null");

		String fileHead = getFileHeader(is);

		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}

		fileHead = fileHead.toUpperCase();

		FileType[] fileTypes = FileType.values();

		for (FileType type : fileTypes) {
			if (fileHead.startsWith(type.getValue())) {
				return type;
			}
		}

		return null;
	}

	/**
	 * Bytes file header to hex string
	 * 
	 * @param src the file header byter
	 * @return the hex string
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Get the file header with input stream
	 * 
	 * @param is the input stream
	 * @return the file header
	 * @throws IOException if read the file has exception throw this
	 */
	private static String getFileHeader(InputStream is) throws IOException {
		byte[] b = new byte[28];

		try {
			is.read(b, 0, 28);
		} catch (IOException e) {
			log.error("Read file has error!", e);
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("Close file has error!", e);
					throw e;
				}
			}
		}
		return bytesToHexString(b);
	}
}
