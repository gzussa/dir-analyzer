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
package org.gz.diranalyzer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.gz.diranalyzer.entity.Structure;
import org.gz.diranalyzer.entity.SystemFile;
import org.gz.diranalyzer.logger.Logger;
import org.gz.diranalyzer.om.ObjectMapper;
import org.gz.diranalyzer.util.FileUtil;

public class Scanner {
	private String pathToScan = null;
	
	private String basePath = null;
	
	private String resultFilePath = null;
	
	private String fileExtension = null;
	
	private boolean isDebug = false;
	
	public Scanner(String pathToScan, String resultFilePath, String fileExtension, boolean isDebug) {
		super();
		this.pathToScan = pathToScan;
		this.resultFilePath = resultFilePath;
		this.fileExtension = fileExtension;
		this.isDebug = isDebug;
	}

	public Structure scan() throws URISyntaxException, IOException{
		//Fetch base file
		File folder = FileUtil.findFromPath(pathToScan);
		
		//We make sure that the base file is a Folder
		if(!folder.isDirectory()){
			throw new RuntimeException("Path ("+basePath+") to scan should reference a folder only");
		}
		//Evaluate the base path based on the root folder
		if(folder.getParentFile() != null){
			basePath = folder.getParentFile().getCanonicalPath();
		}else{
			basePath = "";	
		}
		Logger.debug(isDebug, "basePath: "+basePath);
		
		//Start scanning the given path  
		SystemFile baseFile = createSystemFileForNonFile(folder); 
		List<SystemFile> systemFiles = new ArrayList<SystemFile>();
		//Add base folder scan result to the systemFile list
		systemFiles.add(baseFile);
		//Add all other sub Files and folders
		systemFiles.addAll(ckdir_recursive(folder));
		
		//Create result structure for this scan 		
		Structure currentStructure = new Structure(); 
		currentStructure.setDate(new GregorianCalendar());
		//Add the SystemFile list to the structure object	
		currentStructure.setSystemFiles(systemFiles);
		//output the structure if needed
		Logger.debug(isDebug, "Full Scan File Path: "+resultFilePath);
		ObjectMapper.outputStructure(currentStructure, resultFilePath, fileExtension);
		//return the structure
		return currentStructure;
	}
	
	private List<SystemFile> ckdir_recursive(File folder) throws IOException{
		List<SystemFile> result = new ArrayList<SystemFile>();
		File[] listOfFiles = folder.listFiles(); 
		if(listOfFiles == null){return result;} 
		
		for (int i = 0; i < listOfFiles.length; i++) {
			File currentFile = listOfFiles[i];
			//If the file object represent a directory
			SystemFile currentSystemFile;
			if (!currentFile.isFile()) {
				currentSystemFile = createSystemFileForNonFile(currentFile);
				result.add(currentSystemFile);
				//We move on to the sub directory
		    	result.addAll(ckdir_recursive(currentFile));
		    }else{
		    	currentSystemFile = createSystemFileForFile(currentFile);
		    	result.add(currentSystemFile);		    			    	  	    	  
		    }
		}
		return result;
	}
	
	private SystemFile createSystemFileForNonFile(File file) throws IOException {
		if(file == null){
			throw new Error("Unexpected exception occurs");
		}
		SystemFile result = new SystemFile();
		//If the file object represent a directory
		result.setIsFile(false);
		result.setName(file.getName());
		result.setIsSymbolicLink(false);
		result.setBaseName(FilenameUtils.getBaseName(file.getName()));
		result.setChecksum(null);
		result.setExtension(null);
		result.setDirectory(FileUtil.removeBasePath(file.getParentFile() != null?file.getParentFile().getCanonicalPath():"", basePath));
		result.setPath(FileUtil.removeBasePath(file.getCanonicalPath(), basePath));
		result.setSize(file.length());
		
		return result;
	}

	private SystemFile createSystemFileForFile(File file) throws IOException {
		if(file == null){
			throw new Error("Unexpected exception occurs");
		}
		SystemFile result = new SystemFile();
		result.setIsFile(true);
		result.setName(file.getName());
		result.setIsSymbolicLink(FileUtils.isSymlink(file));
		result.setBaseName(FilenameUtils.getBaseName(file.getName()));
		result.setChecksum(FileUtil.getMD5CheckSum(file));
		result.setExtension(FilenameUtils.getExtension(file.getName()));
		result.setDirectory(FileUtil.removeBasePath(file.getParentFile() != null?file.getParentFile().getCanonicalPath():"", basePath));
		result.setPath(FileUtil.removeBasePath(file.getCanonicalPath(), basePath));
		result.setSize(file.length());
		
		return result;
	}
}
