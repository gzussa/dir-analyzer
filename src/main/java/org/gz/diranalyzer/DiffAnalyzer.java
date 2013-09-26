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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gz.diranalyzer.constant.Constants;
import org.gz.diranalyzer.entity.Structure;
import org.gz.diranalyzer.entity.SystemFile;
import org.gz.diranalyzer.logger.Logger;
import org.gz.diranalyzer.om.ObjectMapper;
import org.gz.diranalyzer.om.xml.OXMarshaller;

public class DiffAnalyzer {
	
	private String previousScanResultFilePath = null;
	
	private String resultFilePath = null;

	private String fileExtension = null;
	
	private boolean isDebug = false;	
	
	public DiffAnalyzer(String previousScanResultFilePath, String resultFilePath, String fileExtension, boolean isDebug) {
		super();
		this.previousScanResultFilePath = previousScanResultFilePath;
		this.resultFilePath = resultFilePath;
		this.fileExtension = fileExtension;
		this.isDebug = isDebug;
	}

	public Structure performDiff(Structure currentStructure){
		//Load the structure file we want to use to do the diff using the previous generated structure above
		Structure previousStructure = null;
		if(previousScanResultFilePath != null){
			Logger.debug(isDebug, "Previous Scan File Path: "+previousScanResultFilePath);
			previousStructure = OXMarshaller.unmarshall(previousScanResultFilePath);
		}
		//Perform the diff between the previous structure and the current one
		Structure diffStructure = diffBetweenStructures(previousStructure, currentStructure);
		//output the result of the diff
		Logger.debug(isDebug, "Diff Scan File Path: "+resultFilePath);
		ObjectMapper.outputStructure(diffStructure, resultFilePath, fileExtension);
		return diffStructure;
	}

	public Structure diffBetweenStructures(Structure previousStructure, Structure currentStructure) {
		Structure diffStructure = new Structure();
		
		// Transform the currentSystemFiles list into a map to get better performance
		List<SystemFile> currentSystemFiles = currentStructure.getSystemFiles();
		Map<String, SystemFile> currentSystemFilesMap = new HashMap<String, SystemFile>();
		for(Iterator<SystemFile> currentSystemFilesIterator = currentSystemFiles.iterator(); currentSystemFilesIterator.hasNext();){
			SystemFile currentSystemFile = currentSystemFilesIterator.next();
			currentSystemFilesMap.put(currentSystemFile.getPath(), currentSystemFile);
		}
		if(previousStructure != null){
			List<SystemFile> previousSystemFiles = previousStructure.getSystemFiles();
			for(Iterator<SystemFile> previousSystemFilesIterator = previousSystemFiles.iterator(); previousSystemFilesIterator.hasNext();){
				SystemFile previousSystemFile = previousSystemFilesIterator.next();
				String previousPath = previousSystemFile.getPath();
				//Search for the same path in the current structure
				SystemFile currentSystemFile = currentSystemFilesMap.get(previousPath);
				if(currentSystemFile == null){
					//If current system file is null, then it means that the file has been deleted
					SystemFile diffSystemFile = new SystemFile(previousSystemFile);
					diffSystemFile.setAction(Constants.DIFF_STATUS_DELETED);
					diffStructure.addSystemFile(diffSystemFile);
				}else{
					//If current system file exist
					//If system file is a directory then it means that the directory hasn't changed
					//If system file is a file we compare checksum. If check sum are different then it means that the file has been updated
					if(previousSystemFile.getChecksum() != null && !previousSystemFile.getChecksum().equals(currentSystemFile.getChecksum())){
						SystemFile diffSystemFile = new SystemFile(currentSystemFile);
						diffSystemFile.setAction(Constants.DIFF_STATUS_UPDATED);
						diffStructure.addSystemFile(diffSystemFile);
					}
					//We remove the current system file from the map
					currentSystemFilesMap.remove(previousPath);
				}			
			}
		}
		//At this point, whatever is left in currentSystemFilesMap are system file newly created
		for(Iterator<SystemFile> currentSystemFileMapIterator = currentSystemFilesMap.values().iterator(); currentSystemFileMapIterator.hasNext();){
			SystemFile currentSystemFile = currentSystemFileMapIterator.next();
			SystemFile diffSystemFile = new SystemFile(currentSystemFile);
			diffSystemFile.setAction(Constants.DIFF_STATUS_CREATED);
			diffStructure.addSystemFile(diffSystemFile);
		}
		return diffStructure;
	}
	
}
