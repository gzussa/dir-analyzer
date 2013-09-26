/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Gregory
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
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
