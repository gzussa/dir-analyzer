package org.gz.diranalyzer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.codec.digest.DigestUtils;
import org.gz.diranalyzer.constant.Constants;
import org.gz.diranalyzer.logger.Logger;

public class FileUtil {
	
	public static File findFromPath(String path) throws URISyntaxException{
		File result = null;
		try {
			result = find(path);
		} catch (URISyntaxException e) {
			Logger.error("File or directory not found: "+path);
			throw e;
		}
		
		return result;		
	}
	
	private static File find(String path) throws URISyntaxException{
		File result = null;
		URL resource = ClassLoader.getSystemResource(path); 
		if(resource == null){
			result = new File(path);
		}else{			
			result = new File(resource.toURI());			
		} 
		return result;
	}
	
	public static String removeBasePath(String path, String basePath) {
		String result = path.replaceAll(basePath+File.separator, Constants.EMPTY_STRING);
		result =  result.replaceAll(basePath, Constants.EMPTY_STRING);
		return result;
	}

	public static String getMD5CheckSum(File file) throws IOException{
		String md5 = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			md5 = DigestUtils.md5Hex(fis);
		} catch (FileNotFoundException e) {
			Logger.error("File not found");
			throw e;
		}finally{
			if(fis != null){
				fis.close();
			}
		}		
		return md5;
	}
}
