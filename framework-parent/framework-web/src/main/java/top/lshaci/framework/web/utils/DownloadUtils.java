package top.lshaci.framework.web.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
import top.lshaci.framework.common.exception.BaseException;
import top.lshaci.framework.web.exception.WebBaseException;

/**
 * Download utils
 * 
 * @author lshaci
 * @since 0.0.4
 * @version 0.0.4
 */
@Slf4j
public class DownloadUtils {
	
	/**
	 * The data cache size
	 */
	public static int cacheSize;

	/**
     * Download excel file
     * 
     * @param fileName the download file name
     * @param outputStream the byte array output stream of the file
     * @param response the http servlet response
     */
    public static void downloadExcel(String fileName, ByteArrayOutputStream outputStream, HttpServletResponse response) {
        if (StringUtils.isBlank(fileName)) {
            throw new BaseException("Excel文件文件名不能为空");
        }

        try (
                InputStream fileInputStream = new ByteArrayInputStream(outputStream.toByteArray());
                ServletOutputStream os = response.getOutputStream();
        ) {
            writeExcelFile(fileName, response, fileInputStream, os);
        } catch (Exception e) {
            log.error("导出[" + fileName + "]失败", e);
            throw new WebBaseException("导出[" + fileName + "]失败", e);
        }
    }

    /**
     * Download excel file
     * 
     * @param fileName the download file name
     * @param fileInputStream the file input stream
     * @param response the http servlet response
     */
    public static void downloadExcel(String fileName, FileInputStream fileInputStream, HttpServletResponse response) {
        if (StringUtils.isBlank(fileName)) {
            throw new BaseException("Excel文件文件名不能为空");
        }
        
        try (
                ServletOutputStream os = response.getOutputStream();
        ) {
            writeExcelFile(fileName, response, fileInputStream, os);
        } catch (Exception e) {
            log.error("导出[" + fileName + "]失败", e);
            throw new WebBaseException("导出[" + fileName + "]失败", e);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.warn("Ignore the resource close exception", e);
            }
        }
    }
    
    /**
     * Download excel file
     * 
     * @param fileName the download file name
     * @param filePath the path of the file
     * @param response the http servlet response
     */
    public static void downloadExcel(String fileName, String filePath, HttpServletResponse response) {
        if (StringUtils.isBlank(fileName)) {
            throw new BaseException("Excel文件文件名不能为空");
        }
        
        try (
                InputStream fileInputStream = new FileInputStream(filePath);
                ServletOutputStream os = response.getOutputStream();
        ) {
            writeExcelFile(fileName, response, fileInputStream, os);
        } catch (Exception e) {
            log.error("导出[" + fileName + "]失败", e);
            throw new WebBaseException("导出[" + fileName + "]失败", e);
        }
    }
    
    /**
     * Write out excel file
     * 
     * @param fileName the excel file name
     * @param response the http servlet response
     * @param fileInputStream the input stream of the excel file
     * @param os the servlet output stream
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private static void writeExcelFile(String fileName, HttpServletResponse response, InputStream fileInputStream,
            ServletOutputStream os) throws UnsupportedEncodingException, IOException {
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        byte[] b = new byte[cacheSize];
        int length;
        while ((length = fileInputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
    }
}