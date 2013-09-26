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
package com.gz.diranalyzer;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.gz.diranalyzer.DiffAnalyzer;
import org.gz.diranalyzer.constant.Constants;
import org.gz.diranalyzer.entity.Structure;
import org.gz.diranalyzer.entity.SystemFile;
import org.junit.Test;

public class DiffAnalyzerTest {
	
	@Test
	public void testSimpleCreatedStructure() {		
		Structure newStructure = new Structure();
		SystemFile newDir = new SystemFile();
		newDir.setChecksum(null);
		newDir.setExtension(null);
		newDir.setIsFile(false);
		newDir.setIsSymbolicLink(false);
		newDir.setName("dir");
		newDir.setBaseName("dir");
		newDir.setPath("dir");
		newDir.setSize(1l);
		newDir.setDirectory("");
		newStructure.addSystemFile(newDir);
		
		SystemFile newfileA = new SystemFile();
		newfileA.setChecksum("1");
		newfileA.setExtension("txt");
		newfileA.setIsFile(true);
		newfileA.setIsSymbolicLink(false);
		newfileA.setName("fileA.txt");
		newfileA.setBaseName("fileA");
		newfileA.setPath("dir/fileA.txt");
		newfileA.setSize(2l);
		newfileA.setDirectory("dir");
		newStructure.addSystemFile(newfileA);		
		
		DiffAnalyzer diffAnalyzer = new DiffAnalyzer(null, null, null, false);
		Structure diffStructure = diffAnalyzer.diffBetweenStructures(null, newStructure);
		
		List<SystemFile> diffSystemFiles = diffStructure.getSystemFiles();
		assertTrue(diffSystemFiles.size() == 2);
		for(Iterator<SystemFile> diffSystemFilesIterator = diffSystemFiles.iterator(); diffSystemFilesIterator.hasNext();){
			SystemFile currentSystemFile = diffSystemFilesIterator.next();
			if(currentSystemFile.getPath().equals(newDir.getPath())){
				assertTrue(currentSystemFile.getChecksum() == null);
				assertTrue(currentSystemFile.getExtension() == null);
				assertTrue(currentSystemFile.isFile() == false);
				assertTrue(currentSystemFile.isSymbolicLink() == false);
				assertTrue(currentSystemFile.getName().equals("dir"));
				assertTrue(currentSystemFile.getBaseName().equals("dir"));
				assertTrue(currentSystemFile.getPath().equals("dir"));
				assertTrue(currentSystemFile.getSize().equals(1l));
				assertTrue(currentSystemFile.getDirectory().equals(""));
				assertTrue(currentSystemFile.getAction().equals(Constants.DIFF_STATUS_CREATED));
			}else if(currentSystemFile.getPath().equals(newfileA.getPath())){ 
				assertTrue(currentSystemFile.getChecksum().equals("1"));
				assertTrue(currentSystemFile.getExtension().equals("txt"));
				assertTrue(currentSystemFile.isFile() == true);
				assertTrue(currentSystemFile.isSymbolicLink() == false);
				assertTrue(currentSystemFile.getName().equals("fileA.txt"));
				assertTrue(currentSystemFile.getBaseName().equals("fileA"));
				assertTrue(currentSystemFile.getPath().equals("dir/fileA.txt"));
				assertTrue(currentSystemFile.getSize().equals(2l));
				assertTrue(currentSystemFile.getDirectory().equals("dir"));
				assertTrue(currentSystemFile.getAction().equals(Constants.DIFF_STATUS_CREATED));
			}else{
				fail();
			}
		}		
	}
	
	@Test
	public void testSimpleStructure() {
		Structure oldStructure = new Structure();
		SystemFile dir = new SystemFile();
		dir.setChecksum(null);
		dir.setPath("dir");
		oldStructure.addSystemFile(dir);
		
		SystemFile fileA = new SystemFile();
		fileA.setChecksum("1");
		fileA.setPath("dir/fileA.txt");
		oldStructure.addSystemFile(fileA);
		
		SystemFile fileB = new SystemFile();
		fileB.setChecksum("2");
		fileB.setPath("dir/fileB.txt");
		oldStructure.addSystemFile(fileB);
		
		SystemFile fileC = new SystemFile();
		fileC.setChecksum("3");
		fileC.setPath("dir/fileC.txt");
		oldStructure.addSystemFile(fileC);
		
		Structure newStructure = new Structure();
		SystemFile newDir = new SystemFile();
		newDir.setChecksum(null);
		newDir.setExtension(null);
		newDir.setIsFile(false);
		newDir.setIsSymbolicLink(false);
		newDir.setName("dir");
		newDir.setBaseName("dir");
		newDir.setPath("dir");
		newDir.setSize(1l);
		newDir.setDirectory("");
		newStructure.addSystemFile(newDir);
		
		SystemFile newfileA = new SystemFile();
		newfileA.setChecksum("1");
		newfileA.setExtension("txt");
		newfileA.setIsFile(true);
		newfileA.setIsSymbolicLink(false);
		newfileA.setName("fileA.txt");
		newfileA.setBaseName("fileA");
		newfileA.setPath("dir/fileA.txt");
		newfileA.setSize(2l);
		newfileA.setDirectory("dir");
		newStructure.addSystemFile(newfileA);
		
		SystemFile newfileC = new SystemFile();
		newfileC.setChecksum("3updated");
		newfileC.setExtension("txt");
		newfileC.setIsFile(true);
		newfileC.setIsSymbolicLink(false);
		newfileC.setName("fileC.txt");
		newfileC.setBaseName("fileC");
		newfileC.setPath("dir/fileC.txt");
		newfileC.setSize(4l);
		newfileC.setDirectory("dir");
		newStructure.addSystemFile(newfileC);
		
		SystemFile newfileD = new SystemFile();
		newfileD.setChecksum("4");
		newfileD.setExtension("txt");
		newfileD.setIsFile(true);
		newfileD.setIsSymbolicLink(false);
		newfileD.setName("fileD.txt");
		newfileD.setBaseName("fileD");
		newfileD.setPath("dir/fileD.txt");
		newfileD.setSize(5l);
		newfileD.setDirectory("dir");
		newStructure.addSystemFile(newfileD);
		
		DiffAnalyzer diffAnalyzer = new DiffAnalyzer(null, null, null, false);
		Structure diffStructure = diffAnalyzer.diffBetweenStructures(oldStructure, newStructure);
		
		List<SystemFile> diffSystemFiles = diffStructure.getSystemFiles();
		assertTrue(diffSystemFiles.size() == 3);
		for(Iterator<SystemFile> diffSystemFilesIterator = diffSystemFiles.iterator(); diffSystemFilesIterator.hasNext();){
			SystemFile currentSystemFile = diffSystemFilesIterator.next();
			if(currentSystemFile.getPath().equals(fileB.getPath())){
				assertTrue(currentSystemFile.getChecksum().equals("2"));
				assertTrue(currentSystemFile.getPath().equals("dir/fileB.txt"));
				assertTrue(currentSystemFile.getAction().equals(Constants.DIFF_STATUS_DELETED));
			}else if(currentSystemFile.getPath().equals(newfileC.getPath())){
				assertTrue(currentSystemFile.getChecksum().equals("3updated"));
				assertTrue(currentSystemFile.getExtension().equals("txt"));
				assertTrue(currentSystemFile.isFile() == true);
				assertTrue(currentSystemFile.isSymbolicLink() == false);
				assertTrue(currentSystemFile.getName().equals("fileC.txt"));
				assertTrue(currentSystemFile.getBaseName().equals("fileC"));
				assertTrue(currentSystemFile.getPath().equals("dir/fileC.txt"));
				assertTrue(currentSystemFile.getSize().equals(4l));
				assertTrue(currentSystemFile.getDirectory().equals("dir"));
				assertTrue(currentSystemFile.getAction().equals(Constants.DIFF_STATUS_UPDATED));
			}else if(currentSystemFile.getPath().equals(newfileD.getPath())){
				assertTrue(currentSystemFile.getChecksum().equals("4"));
				assertTrue(currentSystemFile.getExtension().equals("txt"));
				assertTrue(currentSystemFile.isFile() == true);
				assertTrue(currentSystemFile.isSymbolicLink() == false);
				assertTrue(currentSystemFile.getName().equals("fileD.txt"));
				assertTrue(currentSystemFile.getBaseName().equals("fileD"));
				assertTrue(currentSystemFile.getPath().equals("dir/fileD.txt"));
				assertTrue(currentSystemFile.getSize().equals(5l));
				assertTrue(currentSystemFile.getDirectory().equals("dir"));
				assertTrue(currentSystemFile.getAction().equals(Constants.DIFF_STATUS_CREATED));
			}else{
				fail();
			}
		}		
	}
}
